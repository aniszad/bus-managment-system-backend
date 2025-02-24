package com.azcode.demo.modules.jwtauth.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    USER, ADMIN;

    fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_$name"))
    }
}