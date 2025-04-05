package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.controller.auth.AuthenticationRequest
import com.azcode.demo.modules.jwtauth.controller.auth.AuthenticationResponse
import com.azcode.demo.modules.jwtauth.config.JwtProperties
import com.azcode.demo.modules.jwtauth.controller.auth.RefreshTokenResponse
import com.azcode.demo.modules.jwtauth.exception.CustomAuthenticationException
import com.azcode.demo.modules.jwtauth.security.CustomUserDetails
import com.azcode.demo.modules.jwtauth.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
  private val authManager: AuthenticationManager,
  private val userDetailsService: CustomUserDetailsService,
  private val tokenService: TokenService,
  private val jwtProperties: JwtProperties,
  private val refreshTokenRepository: RefreshTokenRepository
) {

  fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
    try {
      authManager.authenticate(
        UsernamePasswordAuthenticationToken(
          authenticationRequest.email,
          authenticationRequest.password
        )
      )

      // Load user details after successful authentication
      val user = userDetailsService.loadUserByUsername(authenticationRequest.email) as CustomUserDetails

      // Generate tokens
      val accessToken = createAccessToken(user)
      val refreshToken = createRefreshToken(user)

      // Return full AuthenticationResponse
      return AuthenticationResponse(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        phoneNumber = user.phoneNumber,
        role = user.role,
        accessToken = accessToken,
        refreshToken = refreshToken,
      )
    } catch (e: UsernameNotFoundException) {
      throw CustomAuthenticationException("User not found. Please check your email and try again.")
    } catch (e: org.springframework.security.authentication.BadCredentialsException) {
      throw CustomAuthenticationException("Invalid email or password. Please try again.")
    } catch (e: org.springframework.security.core.AuthenticationException) {
      throw CustomAuthenticationException("Authentication failed. Contact support if the issue persists.")
    }
  }


  fun refreshAccessToken(refreshToken: String): RefreshTokenResponse {
    val extractedEmail = tokenService.extractEmail(refreshToken)
      ?: throw IllegalArgumentException("Invalid refresh token")

    val currentUserDetails = userDetailsService.loadUserByUsername(extractedEmail)
    val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)

    if (tokenService.isExpired(refreshToken) || refreshTokenUserDetails?.username != currentUserDetails.username) {
      throw SecurityException("Refresh token is expired or invalid")
    }

    return RefreshTokenResponse(createAccessToken(currentUserDetails))
  }


  private fun createAccessToken(user: UserDetails) = tokenService.generate(
    userDetails = user,
    expirationDate = getAccessTokenExpiration()
  )

  private fun createRefreshToken(user: UserDetails) = tokenService.generate(
    userDetails = user,
    expirationDate = getRefreshTokenExpiration()
  )

  private fun getAccessTokenExpiration(): Date =
    Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

  private fun getRefreshTokenExpiration(): Date =
    Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)

}