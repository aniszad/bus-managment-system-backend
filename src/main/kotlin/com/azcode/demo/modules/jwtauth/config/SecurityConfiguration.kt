package com.azcode.demo.modules.jwtauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
  private val authenticationProvider: AuthenticationProvider
) {

  @Bean
  fun securityFilterChain(
    http: HttpSecurity,
    jwtAuthenticationFilter: JwtAuthenticationFilter
  ): DefaultSecurityFilterChain {
    http
      .csrf { it.disable() }
      .authorizeHttpRequests {
        it
          // ✅ Allow auth & error endpoints without authentication
          .requestMatchers(
            "/api/auth/login",
            "/api/auth/refresh",
            "/error"
          ).permitAll()

          // ✅ Allow user registration WITHOUT authentication
          .requestMatchers(HttpMethod.POST, "/api/user/create").permitAll()

          // ✅ Restrict all other user-related actions to ADMIN only
          .requestMatchers("/api/user/**").hasRole("ADMIN")

          // ✅ Require authentication for everything else
          .anyRequest().authenticated()
      }
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

    return http.build()
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

}