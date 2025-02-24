package com.azcode.demo.modules.jwtauth.controller.user

import com.azcode.demo.modules.jwtauth.service.UserService
import com.azcode.demo.modules.jwtauth.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = ["*"])
class UserController(
  private val userService: UserService
) {
  private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

  @PostMapping("/create")
  @CrossOrigin(origins = ["*"])
  fun create(@RequestBody userRequest: UserRequest): UserResponse {
    logger.error(userRequest.toString())
    print(userRequest)
    return userService.createUser(userRequest.toModel())
      ?.toResponse()
      ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")
  }

  @GetMapping
  fun listAll(): List<UserResponse> =
    userService.findAll()
      .map { it.toResponse() }

  @GetMapping("/{uuid}")
  fun findByUUID(@PathVariable uuid: UUID): UserResponse =
    userService.findByUUID(uuid)
      ?.toResponse()
      ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

  @DeleteMapping("/{uuid}")
  fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
    val success = userService.deleteByUUID(uuid)

    return if (success)
      ResponseEntity.noContent().build()
    else
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
  }

  private fun User.toResponse(): UserResponse =
    UserResponse(
      uuid = this.id,
      email = this.email,
    )

  private fun UserRequest.toModel(): User =
    User(
      id = UUID.randomUUID(),
      firstName = this.firstName,
      lastName = this.lastName,
      email = this.email,
      phoneNumber = this.phoneNumber ?: "",
      password = this.password,
      role = this.role,
      createdAt = Date(System.currentTimeMillis())
    )
}
