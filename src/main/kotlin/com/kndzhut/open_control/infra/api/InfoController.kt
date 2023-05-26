package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.info.kno.GetAllKnoUseCase
import com.kndzhut.open_control.usecase.info.kno.GetMeasuresByKnoRequest
import com.kndzhut.open_control.usecase.info.kno.GetMeasuresByKnoUseCase
import com.kndzhut.open_control.usecase.info.user.*
import com.kndzhut.open_control.usecase.init_database.InitInfoDatabaseUseCase
import com.kndzhut.open_control.usecase.utils.EmptyRequest
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RestController
@RequestMapping(
    value = ["/"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Info API", description = "API для работы с дополнительной информацией")
class InfoController(
    private val initDatabaseUseCase: InitInfoDatabaseUseCase,
    private val getAllKnoUseCase: GetAllKnoUseCase,
    private val getAllMeasuresByKnoUseCase: GetMeasuresByKnoUseCase,

    private val createUserUseCase: CreateUserUseCase,
    private val updateBusinessUserInfoUseCase: UpdateBusinessUserInfoUseCase,
    private val updateInspectionUserInfoUseCase: UpdateInspectionUserInfoUseCase,

    private val getBusinessUserInfoUseCase: GetBusinessUserInfoUseCase,
    private val getInspectionUserInfoUseCase: GetInspectionUserInfoUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase
) {
    @PostConstruct
    fun getInfoFromGoogleSheets() {
        initDatabaseUseCase.execute()
    }

    @Operation(
        method = "GET", description = "получение списка всех доступных КНО"
    )
    @GetMapping("/knos")
    fun getFreeAppointments(): ResponseEntity<*> =
        getAllKnoUseCase.execute(request = EmptyRequest()).toResponseEntity()

    @Operation(
        method = "GET", description = "получение списка всех доступных видов контроля"
    )
    @GetMapping("/measures")
    fun getFreeAppointments(@RequestParam knoId: Int): ResponseEntity<*> {
        val request = GetMeasuresByKnoRequest(knoId)
        return getAllMeasuresByKnoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "POST", description = "создание пользователя"
    )
    @PostMapping("/user")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<*> {
        return createUserUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "PUT", description = "обновление информации о бизнесе"
    )
    @PostMapping("/business-info/update")
    fun updateBusiness(@RequestBody request: UpdateBusinessUserInfoRequest): ResponseEntity<*> {
        return updateBusinessUserInfoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "PUT", description = "обновление информации о бизнесе"
    )
    @PostMapping("/inspection-info/update")
    fun updateInspector(@RequestBody request: UpdateInspectionUserInfoRequest): ResponseEntity<*> {
        return updateInspectionUserInfoUseCase.execute(request).toResponseEntity()
    }


    @Operation(
        method = "GET", description = "получить роль пользователя"
    )
    @GetMapping("/users/role")
    fun getUserRole(@RequestParam userId: String): ResponseEntity<*> {
        val request = GetUserRoleRequest(userId)
        return getUserRoleUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET", description = "получить роль пользователя"
    )
    @GetMapping("/business-info")
    fun getBusinessUserInfo(@RequestParam userId: String): ResponseEntity<*> {
        val request = GetBusinessUserInfoRequest(userId)
        return getBusinessUserInfoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET", description = "получить роль пользователя"
    )
    @GetMapping("/inspection-info")
    fun getInspectorUserInfo(@RequestParam userId: String): ResponseEntity<*> {
        val request = GetInspectionUserInfoRequest(userId)
        return getInspectionUserInfoUseCase.execute(request).toResponseEntity()
    }
}