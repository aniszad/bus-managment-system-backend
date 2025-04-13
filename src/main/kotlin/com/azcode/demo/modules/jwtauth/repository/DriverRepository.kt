package com.azcode.demo.modules.jwtauth.repository

import com.azcode.demo.modules.jwtauth.model.Driver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DriverRepository : JpaRepository<Driver, UUID> {

    fun findDriverByEmail(email: String): Driver?
    fun findDriverByPhoneNumber(phoneNumber: String): Driver?
}