package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.controller.auth.AuthenticationRequest
import com.azcode.demo.modules.jwtauth.controller.auth.AuthenticationResponse
import com.azcode.demo.modules.jwtauth.config.JwtProperties
import com.azcode.demo.modules.jwtauth.security.CustomUserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
  private val authManager: AuthenticationManager,
  private val userDetailsService: CustomUserDetailsService,
  private val tokenService: TokenService,
  private val jwtProperties: JwtProperties,
) {

  fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
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

    // Return full AuthenticationResponse
    return AuthenticationResponse(
      id = user.id,
      firstName = user.firstName,
      lastName = user.lastName,
      email = user.email,
      phoneNumber = user.phoneNumber,
      role = user.role,
      accessToken = accessToken,
    )
  }


  private fun createAccessToken(user: UserDetails) = tokenService.generate(
    userDetails = user,
    expirationDate = getAccessTokenExpiration()
  )

  private fun getAccessTokenExpiration(): Date =
    Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

}