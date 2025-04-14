package com.azcode.demo.modules.jwtauth.repository

import com.azcode.demo.modules.jwtauth.model.Bus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface BusRepository : JpaRepository<Bus, UUID> {
    fun findBusById(id: UUID): Bus?
    fun findBusByName(name: String): Bus?
    fun existsByName(name: String): Boolean
    fun deleteBusByName(name: String)
    fun findAllByCurrentlyActiveTrue(): List<Bus>
}
