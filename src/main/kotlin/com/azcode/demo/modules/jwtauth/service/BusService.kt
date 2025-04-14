package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.exception.UserAlreadyExistsException
import com.azcode.demo.modules.jwtauth.model.Bus
import com.azcode.demo.modules.jwtauth.model.Driver
import com.azcode.demo.modules.jwtauth.repository.BusRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class BusService(
    private val busRepository: BusRepository,
) {
    fun createBus(bus: Bus): Bus? {
        if (busRepository.findBusByName(bus.name) != null) {
            throw UserAlreadyExistsException("Bus already exists")
        }
        return busRepository.save(bus)
    }

    fun findBusByName(busName: String): Bus? = busRepository.findBusByName(busName)

    fun findAll(): List<Bus> = busRepository.findAll()


    fun findBusById(id: UUID): Bus? = busRepository.findBusById(id)

    fun updateBus(id: UUID, updated: Bus): Bus? {
        val existing = busRepository.findBusById(id)
        return if (existing != null) {
            val toSave = existing.copy(
                name = updated.name,
                currentlyActive = updated.currentlyActive,
                brokenDown = updated.brokenDown,
                licensePlate = updated.licensePlate,
                route = updated.route,
                driver = updated.driver
            )
            busRepository.save(toSave)
        } else null
    }

    fun deleteBusByName(name: String) {
        if (busRepository.existsByName(name)) {
            busRepository.deleteBusByName(name)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Bus with name $name not found.")
        }
    }
}
