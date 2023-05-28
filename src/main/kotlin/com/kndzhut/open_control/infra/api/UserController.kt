package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.users.CreateUserRequest
import com.kndzhut.open_control.usecase.users.CreateUserUseCase
import com.kndzhut.open_control.usecase.users.GetUserRoleRequest
import com.kndzhut.open_control.usecase.users.GetUserRoleUseCase
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
        method = "GET", description = "получение роли пользователя"
    )
    @PostMapping("/role")
    fun createUser(@RequestParam userId: String): ResponseEntity<*> {
        return getUserRoleUseCase.execute(GetUserRoleRequest(userId)).toResponseEntity()
    }
}