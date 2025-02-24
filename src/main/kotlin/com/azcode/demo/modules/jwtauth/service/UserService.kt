package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.exception.UserAlreadyExistsException
import com.azcode.demo.modules.jwtauth.model.User
import com.azcode.demo.modules.jwtauth.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(user: User): User? {
        val foundByEmail = userRepository.findByEmail(user.email)
        val foundByPhoneNumber = userRepository.findByPhoneNumber(user.phoneNumber)
        val encryptedUser = user.copy(password = passwordEncoder.encode(user.password))
        return if (foundByEmail == null && foundByPhoneNumber == null) {
            userRepository.save(encryptedUser)
            encryptedUser
        } else if (foundByEmail != null && foundByPhoneNumber != null) {
            throw UserAlreadyExistsException("email and phone number already in use")
        } else if (foundByEmail != null) {
            throw UserAlreadyExistsException("email already in use")
        } else {
            throw UserAlreadyExistsException("phone number already in use")
        }

    }


    fun findByUUID(uuid: UUID): User? =
        userRepository.findById(uuid).orElse(null)  // Using `findById` from JpaRepository

    fun findAll(): List<User> =
        userRepository.findAll()  // No need for `toList()` since JpaRepository already returns a List

    fun deleteByUUID(uuid: UUID): Boolean {
        if (userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid)
            return true
        }
        return false
    }
}
