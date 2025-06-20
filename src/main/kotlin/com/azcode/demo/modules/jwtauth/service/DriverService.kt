package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.exception.UserAlreadyExistsException
import com.azcode.demo.modules.jwtauth.model.Driver
import com.azcode.demo.modules.jwtauth.repository.DriverRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class DriverService(
    private val driverRepository: DriverRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createDriver(driver: Driver): Driver? {
        val foundByPhoneNumber = driverRepository.findDriverByPhoneNumber(driver.phoneNumber)
        return if (foundByPhoneNumber == null) {
            driverRepository.save(driver)
            driver
        } else {
            throw UserAlreadyExistsException("phone number already in use")
        }

    }

    fun findByUUID(uuid: UUID): Driver? =
        driverRepository.findById(uuid).orElse(null)  // Using `findById` from JpaRepository

    fun findAll(): List<Driver> =
        driverRepository.findAll()  // No need for `toList()` since JpaRepository already returns a List

    fun deleteByUUID(uuid: UUID): Boolean {
        if (driverRepository.existsById(uuid)) {
            driverRepository.deleteById(uuid)
            return true
        }
        return false
    }


}