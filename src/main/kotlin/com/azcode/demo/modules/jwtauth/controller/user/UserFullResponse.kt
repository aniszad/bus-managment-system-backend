package com.azcode.demo.modules.jwtauth.controller.user

import com.azcode.demo.modules.jwtauth.model.Role
import java.util.*

data class UserFullResponse (
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String = "",
    val role: Role,
    val createdAt: Date = Date(),
)