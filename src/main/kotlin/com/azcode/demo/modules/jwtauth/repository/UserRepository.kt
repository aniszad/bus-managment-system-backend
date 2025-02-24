package com.azcode.demo.modules.jwtauth.repository

import com.azcode.demo.modules.jwtauth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {

  fun findByEmail(email: String): User?
  fun findByPhoneNumber(phoneNumber: String): User?

}
