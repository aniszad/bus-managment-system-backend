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
    fun create(@RequestBody busRequest: BusRequest): Bus {
        logger.info("Creating bus: $busRequest")
        return busService.createBus(busRequest.toModel())
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create bus.")
    }

    @GetMapping("/all")
    fun listAll(): List<Bus> = busService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): Bus =
        busService.findBusById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found.")

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable name: String): Bus =
        busService.findBusByName(name)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found by name.")

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
            route = if (this.routeId != null) routeService.findRouteById(this.routeId) else null,
            driver = if (this.driverId != null) driverService.findByUUID(this.driverId) else null,
        )
}

