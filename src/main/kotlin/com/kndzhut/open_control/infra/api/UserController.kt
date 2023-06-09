package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.users.*
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    value = ["/user"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "User API", description = "API для работы с пользователями")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val registryUserUseCase: RegistryUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase
) {
    @Operation(
        method = "POST", description = "создание пользователя"
    )
    @PostMapping("")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<*> {
        return createUserUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "POST", description = "регистрация пользователя"
    )
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegistryUserRequest): ResponseEntity<*> {
        return registryUserUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "POST", description = "создание пользователя"
    )
    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginUserRequest): ResponseEntity<*> {
        return loginUserUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET", description = "получение роли пользователя"
    )
    @PostMapping("/role")
    fun createUser(@RequestParam userId: String): ResponseEntity<*> {
        return getUserRoleUseCase.execute(GetUserRoleRequest(userId)).toResponseEntity()
    }
}