package com.azcode.demo.modules.jwtauth.controller.bus

import java.util.UUID

data class BusRequest (
    val name: String,
    val currentlyActive: Boolean = false,
    val brokenDown: Boolean = false,
    val licensePlate: String,
    val route: String? = null,
    val driver: String? = null,
)