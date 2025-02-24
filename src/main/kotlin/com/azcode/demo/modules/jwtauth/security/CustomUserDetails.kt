package com.azcode.demo.modules.jwtauth.security

import com.azcode.demo.modules.jwtauth.model.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class CustomUserDetails(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String = "",
    val role: Role,
    private val passwordHash: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = passwordHash
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
