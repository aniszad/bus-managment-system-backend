package com.azcode.demo.modules.jwtauth.controller.user

import java.util.*

data class UserResponse(
  val uuid: UUID,
  val email: String,
)