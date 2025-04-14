package com.azcode.demo.modules.jwtauth.controller.bus

import java.util.UUID

data class BusRequest (
    val name: String,
    val currentlyActive: Boolean,
    val brokenDown: Boolean,
    val licensePlate: String,
    val routeId : UUID?,
    val driverId : UUID?,

)