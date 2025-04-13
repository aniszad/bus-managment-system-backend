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
        val foundByEmail = driverRepository.findDriverByEmail(driver.email)
        val foundByPhoneNumber = driverRepository.findDriverByPhoneNumber(driver.phoneNumber)
        val encryptedDriver = driver.copy(password = passwordEncoder.encode(driver.password))
        return if (foundByEmail == null && foundByPhoneNumber == null) {
            driverRepository.save(encryptedDriver)
            encryptedDriver
        } else if (foundByEmail != null && foundByPhoneNumber != null) {
            throw UserAlreadyExistsException("email and phone number already in use")
        } else if (foundByEmail != null) {
            throw UserAlreadyExistsException("email already in use")
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