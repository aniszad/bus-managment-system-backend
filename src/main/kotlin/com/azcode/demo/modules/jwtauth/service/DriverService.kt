package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.controller.driver.DriverRequest
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

    fun updateDriverInfo(driverRequest: DriverRequest): Driver {
        val driver = driverRepository.findById(UUID.fromString(driverRequest.driverId))
            .orElseThrow()

        driver.phoneNumber = driverRequest.phoneNumber
        driver.firstName = driverRequest.firstName
        driver.lastName = driverRequest.lastName
        driver.isActive = driverRequest.isActive

        return driverRepository.save(driver)
    }


    fun deleteByUUID(uuid: UUID): Boolean {
        if (driverRepository.existsById(uuid)) {
            driverRepository.deleteById(uuid)
            return true
        }
        return false
    }


}