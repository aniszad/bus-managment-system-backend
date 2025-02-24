package com.azcode.demo.modules.jwtauth.controller.user

import com.azcode.demo.modules.jwtauth.model.Role


data class UserRequest(
  val firstName: String,
  val lastName: String,
  val email: String,
  val phoneNumber: String?,
  val password: String,
  val role: Role
)

