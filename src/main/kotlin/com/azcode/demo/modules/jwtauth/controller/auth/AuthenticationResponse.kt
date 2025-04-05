package com.azcode.demo.modules.jwtauth.controller.auth

import com.azcode.demo.modules.jwtauth.model.Role
import java.util.*

data class AuthenticationResponse(
  val id: UUID,
  val firstName: String,
  val lastName: String,
  val email: String,
  val phoneNumber: String,
  val role: Role,
  val accessToken: String,
  val refreshToken: String
)