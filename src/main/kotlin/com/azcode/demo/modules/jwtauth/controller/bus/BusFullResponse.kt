package com.azcode.demo.modules.jwtauth.controller.bus

import com.azcode.demo.modules.jwtauth.controller.driver.DriverFullResponse
import com.azcode.demo.modules.jwtauth.model.Driver
import com.azcode.demo.modules.jwtauth.model.Route
import java.util.*

data class BusFullResponse (
    val id:String,
    val code: String,
    val name:String,
    val currentlyActive: Boolean,
    val brokenDown: Boolean,
    val licensePlate: String,
    val route: RouteFullResponse?,
    val driver: DriverFullResponse?,
    val createAt: Date,
)