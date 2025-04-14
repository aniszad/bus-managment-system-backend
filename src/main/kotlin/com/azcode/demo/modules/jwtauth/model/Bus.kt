package com.azcode.demo.modules.jwtauth.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.util.*

@Entity
@Table(
    name = "buses",
    uniqueConstraints = [UniqueConstraint(columnNames = ["name"])]
)
data class Bus(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "currently_active", nullable = false)
    val currentlyActive: Boolean = false,

    @Column(name = "broken_down", nullable = false)
    val brokenDown: Boolean = false,

    @Column(name = "license_plate", nullable = false)
    val licensePlate: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", foreignKey = ForeignKey(name = "fk_bus_route"))
    val route: Route? = null, // nullable if a bus can be unassigned

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", foreignKey = ForeignKey(name = "fk_bus_driver"))
    val driver: Driver? = null,

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date(),

    @Version
    var version: Long? = null
)
