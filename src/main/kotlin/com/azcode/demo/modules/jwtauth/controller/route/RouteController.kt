package com.azcode.demo.modules.jwtauth.controller.route

import com.azcode.demo.modules.jwtauth.controller.user.UserRequest
import com.azcode.demo.modules.jwtauth.model.Route
import com.azcode.demo.modules.jwtauth.service.RouteService
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/route")
@CrossOrigin(origins = ["*"])
class RouteController(
    private val routeService: RouteService,
) {

    @PostMapping("/create")
    fun create(@RequestBody routeRequest: RouteRequest) : Route {
        return routeService.createRoute(routeRequest.toModel())
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")
    }
    fun RouteRequest.toModel(): Route =
        Route(
            id = UUID.randomUUID(),
            name = this.routeName,
            routeFile = this.routeFile,
        )
    @GetMapping("/{id}")
    fun getRouteById(@PathVariable id: UUID): Route =
        routeService.findRouteById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found.")


    @GetMapping("/all")
    fun getAllRoutes(): List<Route> = routeService.findAll()

    @PutMapping("/update/{id}")
    fun updateRoute(@PathVariable id: UUID, @RequestBody routeRequest: RouteRequest): Route =
        routeService.updateRoute(id, routeRequest.toModel())
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update. Route not found.")

    @DeleteMapping("/delete/{id}")
    fun deleteRouteByName(@PathVariable id: UUID) {
        routeService.deleteRouteById(id)
    }

}