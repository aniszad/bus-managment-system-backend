package com.azcode.demo.modules.jwtauth.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.util.*


@Entity
@Table(name = "drivers", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class Driver (
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),  // Removed @GeneratedValue since we use UUID

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "phone_number")
    val phoneNumber: String = "",

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date(),

    @Version
    var version: Long? = null
)