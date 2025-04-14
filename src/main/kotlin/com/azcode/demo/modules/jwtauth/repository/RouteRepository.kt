package com.azcode.demo.modules.jwtauth.repository

import com.azcode.demo.modules.jwtauth.model.Route
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RouteRepository : JpaRepository<Route, UUID> {
    fun findRouteById(id: UUID): Route?
    fun findRouteByName(name: String): Route?
    fun existsByName(name: String): Boolean
    fun deleteRouteByName(name: String)
    fun findAllByIsActiveTrue(): List<Route>
}