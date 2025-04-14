package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.model.Route
import com.azcode.demo.modules.jwtauth.repository.RouteRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RouteService(
    private val routeRepository: RouteRepository
) {

    fun findRouteById(id: UUID): Route? = routeRepository.findRouteById(id)
    fun findAll(): List<Route> = routeRepository.findAll()
    fun createRoute(route: Route): Route? = routeRepository.save(route)
    fun deleteRouteById(id: UUID) = routeRepository.deleteById(id)
    fun updateRoute(id: UUID, route: Route): Route? = routeRepository.save(route)

}