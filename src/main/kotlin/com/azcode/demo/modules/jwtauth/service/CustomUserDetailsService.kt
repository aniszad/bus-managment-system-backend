package com.azcode.demo.modules.jwtauth.service

import com.azcode.demo.modules.jwtauth.model.User
import com.azcode.demo.modules.jwtauth.repository.UserRepository
import com.azcode.demo.modules.jwtauth.security.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
  private val userRepository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails =
    userRepository.findByEmail(username)
      ?.toUserDetails()
      ?: throw UsernameNotFoundException("User not found!")

  private fun User.toUserDetails(): CustomUserDetails =
    CustomUserDetails(
      id = this.id,
      firstName = this.firstName,
      lastName = this.lastName,
      email = this.email,
      phoneNumber = this.phoneNumber,
      passwordHash = this.password,
      role = this.role,
      authorities = this.role.getAuthorities() // Convert role to authorities if needed
    )
}
