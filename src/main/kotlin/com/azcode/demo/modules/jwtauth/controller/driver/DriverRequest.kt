package com.azcode.demo.modules.jwtauth.controller.driver

import com.azcode.demo.modules.jwtauth.model.Role
import java.util.*

data class DriverRequest (
    val driverId: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val isActive: Boolean
)


