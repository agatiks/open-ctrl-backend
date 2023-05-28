package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.users.GetInspectionUserInfoRequest
import com.kndzhut.open_control.usecase.users.GetInspectionUserInfoUseCase
import com.kndzhut.open_control.usecase.users.UpdateInspectionUserInfoRequest
import com.kndzhut.open_control.usecase.users.UpdateInspectionUserInfoUseCase
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(
    value = ["/inspection-user"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Info API", description = "API для работы с пользователем-инспектором")
class InspectionUserController(
    //private val createInspectionUserUseCase: CreateInspectionUserUseCase,
    private val updateInspectionUserInfoUseCase: UpdateInspectionUserInfoUseCase,
    private val getInspectionUserInfoUseCase: GetInspectionUserInfoUseCase
) {

    @Operation(
        method = "PUT", description = "обновление информации о бизнесе"
    )
    @PostMapping("/info")
    fun updateInspector(@RequestBody request: UpdateInspectionUserInfoRequest): ResponseEntity<*> {
        return updateInspectionUserInfoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET", description = "получить роль пользователя"
    )
    @GetMapping("/info")
    fun getInspectorUserInfo(@RequestParam userId: String): ResponseEntity<*> {
        val request = GetInspectionUserInfoRequest(userId)
        return getInspectionUserInfoUseCase.execute(request).toResponseEntity()
    }
}