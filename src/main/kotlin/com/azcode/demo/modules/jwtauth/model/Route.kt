package com.azcode.demo.modules.jwtauth.model

import com.azcode.demo.modules.jwtauth.controller.route.RouteFullResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.*

@Entity
@Table(name = "routes", uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
data class Route(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "route_file", nullable = false)
    val routeFile: String

)
fun Route.toFullRouteResponse() : RouteFullResponse {
    return RouteFullResponse(
        id = this.id.toString(),
        name = this.name,
        routeFile = this.routeFile
    )
}