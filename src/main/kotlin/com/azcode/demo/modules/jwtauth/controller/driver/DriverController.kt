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

    @PostMapping("/getAll")
    fun listAll(): List<DriverFullResponse> =
        driverService.findAll()
            .map { it.toFullResponse() }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): DriverResponse =
        driverService.findByUUID(uuid)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
        val success = driverService.deleteByUUID(uuid)

        return if (success)
            ResponseEntity.noContent().build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
    }
    private fun Driver.toResponse() =
        DriverResponse(
            uuid = UUID.randomUUID(),
            email = this.email,
        )

    private fun Driver.toFullResponse() : DriverFullResponse =
        DriverFullResponse(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            phoneNumber = this.phoneNumber,
            role = this.role,
        )

    private fun DriverRequest.toModel() : Driver =
        Driver(
            id = UUID.randomUUID(),
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            phoneNumber = this.phoneNumber ?: "",
            password = this.password,
            role = this.role,
            createdAt = Date(System.currentTimeMillis())
        )



}

