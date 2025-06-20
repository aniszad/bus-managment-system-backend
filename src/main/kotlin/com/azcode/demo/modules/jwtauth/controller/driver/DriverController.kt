package com.azcode.demo.modules.jwtauth.controller.driver

import com.azcode.demo.modules.jwtauth.controller.user.UserController
import com.azcode.demo.modules.jwtauth.model.Driver
import com.azcode.demo.modules.jwtauth.service.DriverService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/api/driver")
@CrossOrigin(origins = ["*"])
class DriverController(
    private val driverService : DriverService
) {
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/create")
    @CrossOrigin(origins = ["*"])
    fun create(@RequestBody driverRequest: DriverRequest): DriverResponse {
        logger.error(driverRequest.toString())
        print(driverRequest)
        return driverService.createDriver(driverRequest.toModel())
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")
    }

    @PostMapping("/all")
    @CrossOrigin(origins = ["*"])
    fun listAll(): List<DriverFullResponse> =
        driverService.findAll()
            .map { it.toFullResponse() }


    private fun Driver.toResponse() =
        DriverResponse(
            uuid = this.id
        )

    private fun Driver.toFullResponse() : DriverFullResponse =
        DriverFullResponse(
            id = this.id.toString(),
            firstName = this.firstName,
            lastName = this.lastName,
            phoneNumber = this.phoneNumber,
            isActive = this.isActive,
            createdAt = this.createdAt,
        )

    private fun DriverRequest.toModel() : Driver =
        Driver(
            id = UUID.randomUUID(),
            firstName = this.firstName,
            lastName = this.lastName,
            phoneNumber = this.phoneNumber ?: "",
            isActive = this.isActive,
            createdAt = Date(System.currentTimeMillis())
        )



}

