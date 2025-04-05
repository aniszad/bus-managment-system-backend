package com.azcode.demo.modules.jwtauth.controller.auth

import com.azcode.demo.modules.jwtauth.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
  private val authenticationService: AuthenticationService
) {

  @PostMapping("/login")
  fun authenticate(
    @RequestBody authRequest: AuthenticationRequest
  ): AuthenticationResponse =
    authenticationService.authentication(authRequest)

  @PostMapping("/refresh")
  fun refreshToken(
    @RequestBody refreshRequest: RefreshTokenRequest
  ) : RefreshTokenResponse =
    authenticationService.refreshAccessToken(refreshRequest.token)

}

