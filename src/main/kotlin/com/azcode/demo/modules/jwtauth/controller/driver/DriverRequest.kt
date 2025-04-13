package com.azcode.demo.modules.jwtauth.controller.driver

import com.azcode.demo.modules.jwtauth.model.Role

data class DriverRequest (
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String?,
    val password: String,
    val role: Role
)


