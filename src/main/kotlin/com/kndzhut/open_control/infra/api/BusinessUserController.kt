package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.users.business.CreateBusinessRequest
import com.kndzhut.open_control.usecase.users.business.CreateBusinessUseCase
import com.kndzhut.open_control.usecase.users.business.GetBusinessRequest
import com.kndzhut.open_control.usecase.users.business.GetBusinessUseCase
import com.kndzhut.open_control.usecase.users.GetBusinessUserInfoRequest
import com.kndzhut.open_control.usecase.users.GetBusinessUserInfoUseCase
import com.kndzhut.open_control.usecase.users.UpdateBusinessUserInfoRequest
import com.kndzhut.open_control.usecase.users.UpdateBusinessUserInfoUseCase
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(
    value = ["/business-user"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Business API", description = "API для работы с бизнес-пользователем")
class BusinessUserController(
    //private val createBusinessUserUseCase: CreateBusinessUserUseCase,
    private val updateBusinessUserInfoUseCase: UpdateBusinessUserInfoUseCase,
    private val getBusinessUserInfoUseCase: GetBusinessUserInfoUseCase,

    private val createBusinessUseCase: CreateBusinessUseCase,
    private val getBusinessUseCase: GetBusinessUseCase,

    /*private val createPassportInfoUseCase: CreatePassportInfoUseCase,
    private val updatePassportInfoUseCase: CreatePassportInfoUseCase,
    private val getPassportInfoUseCase: GetPassportInfoUseCase,*/
) {
    @Operation(
        method = "PUT", description = "обновление информации о бизнесе"
    )
    @PostMapping("/info")
    fun updateBusiness(@RequestBody request: UpdateBusinessUserInfoRequest): ResponseEntity<*> {
        return updateBusinessUserInfoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET", description = "получить роль пользователя"
    )
    @GetMapping("/info")
    fun getBusinessUserInfo(@RequestParam userId: String): ResponseEntity<*> {
        val request = GetBusinessUserInfoRequest(userId)
        return getBusinessUserInfoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "POST", description = "добавить информацию о бизнесе"
    )
    @PostMapping("/info/business")
    fun getInspectorUserInfo(@RequestBody request: CreateBusinessRequest): ResponseEntity<*> {
        return createBusinessUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET", description = "посмотреть информацию о бизнесе"
    )
    @GetMapping("/info/business")
    fun getBusinessInfo(@RequestParam businessId: UUID): ResponseEntity<*> {
        val request = GetBusinessRequest(businessId)
        return getBusinessUseCase.execute(request).toResponseEntity()
    }
}