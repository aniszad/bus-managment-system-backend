package com.azcode.demo.modules.jwtauth.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import kotlin.random.Random

@Entity
@Table(
    name = "buses",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name"]),
        UniqueConstraint(columnNames = ["code"]) // ensure code is unique
    ]
)
data class Bus(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "code", nullable = false, unique = true, length = 6)
    val code: String = generateBusCode(),

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
) {
    companion object {
        // Avoid confusing characters for drivers (e.g., O, I, 0)
        private val ALLOWED_CHARS = (('A'..'Z') + ('1'..'9')) - listOf('O', 'I')

        fun generateBusCode(length: Int = 6): String {
            return (1..length)
                .map { ALLOWED_CHARS.random(Random) }
                .joinToString("")
        }
    }
}

