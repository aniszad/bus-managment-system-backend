package com.azcode.demo.modules.jwtauth.controller.bus

import com.azcode.demo.modules.jwtauth.controller.driver.DriverFullResponse
import com.azcode.demo.modules.jwtauth.controller.driver.DriverRequest
import com.azcode.demo.modules.jwtauth.controller.driver.DriverResponse
import com.azcode.demo.modules.jwtauth.controller.route.RouteController
import com.azcode.demo.modules.jwtauth.controller.user.UserController
import com.azcode.demo.modules.jwtauth.model.Bus
import com.azcode.demo.modules.jwtauth.service.BusService
import com.azcode.demo.modules.jwtauth.service.DriverService
import com.azcode.demo.modules.jwtauth.service.RouteService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
@RestController
@RequestMapping("/api/bus")
@CrossOrigin(origins = ["*"])
class BusController(
    private val busService: BusService,
    private val driverService: DriverService,
    private val routeService: RouteService,
) {
    private val logger: Logger = LoggerFactory.getLogger(BusController::class.java)

    @PostMapping("/create")
    fun create(@RequestBody busRequest: BusRequest): Boolean {
        logger.info("Creating bus: $busRequest")
        return busService.createBus(busRequest.toModel())
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create bus.")
    }

    @GetMapping("/getAll")
    fun listAll(): List<Bus> = busService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): Bus =
        busService.findBusById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found.")

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable name: String): Bus =
        busService.findBusByName(name)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found by name.")

    @GetMapping("/findByCode")
    fun findByCode(@RequestBody busCodeRequest: BusCodeRequest): Bus{
        return busService.findBusByCode(busCodeRequest.busCode) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found.")
    }

    @PutMapping("/update/{id}")
    fun update(@PathVariable id: UUID, @RequestBody busRequest: BusRequest): Bus =
        busService.updateBus(id, busRequest.toModel())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update. Bus not found.")

    @DeleteMapping("/delete/{name}")
    fun deleteByName(@PathVariable name: String) {
        busService.deleteBusByName(name)
    }

    // Mapping function
    fun BusRequest.toModel(): Bus =
        Bus(
            id = UUID.randomUUID(),
            name = this.name,
            currentlyActive = this.currentlyActive,
            brokenDown = this.brokenDown,
            licensePlate = this.licensePlate,
            route = if (this.route != null) routeService.findRouteById(UUID.fromString(this.route)) else null,
            driver = if (this.driver != null) driverService.findByUUID(UUID.fromString(this.driver)) else null,
        )
}

