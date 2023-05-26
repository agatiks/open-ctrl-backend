package com.kndzhut.open_control.infra.api


import com.kndzhut.open_control.usecase.appointments.*
import com.kndzhut.open_control.usecase.init_database.InitAppointmentsDatabaseUseCase
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.DependsOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.PostConstruct

@RestController
@DependsOn("infoController")
@RequestMapping(
    value = ["/appointments"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Appointments API", description = "API для работы со встречами")
class AppointmentsController(
    private val initAppointmentsDatabaseUseCase: InitAppointmentsDatabaseUseCase,
    private val getFreeAppointmentsUseCase: GetFreeAppointmentsUseCase,
    private val getUserAppointmentsUseCase: GetUserAppointmentsUseCase,
    private val selectAppointmentUseCase: SelectAppointmentUseCase,
    private val approveAppointmentUseCase: ApproveAppointmentUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase
) {
    @PostConstruct
    fun getAppointmentsFromGoogleSheets() {
        initAppointmentsDatabaseUseCase.execute()
    }

    @Operation(
        method = "GET",
        description = "получение списка всех доступных окон, начиная с времени запроса"
    )
    @GetMapping("/free")
    fun getFreeAppointments(
        @RequestParam knoId: Int
    ): ResponseEntity<*> {
        val request = GetFreeAppointmentsRequest(knoId)
        return getFreeAppointmentsUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET",
        description = "получение списка всех записей пользователя"
    )
    @GetMapping()
    fun getUserAppointments(
        @RequestParam userId: String
    ): ResponseEntity<*> {
        val request = GetUserAppointmentsRequest(userId)
        return getUserAppointmentsUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "PUT",
        description = "запрос о записи от бизнеса"
    )
    @PutMapping("/select")
    fun requestAppointment(
        @RequestBody request: SelectAppointmentRequest
    ): ResponseEntity<*> {
        return selectAppointmentUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "PUT",
        description = "подтверждение записи от инспекции"
    )
    @PutMapping("/agree")
    fun approveAppointment(
        @RequestBody request: ApproveAppointmentRequest
    ): ResponseEntity<*> {
        return approveAppointmentUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "PUT",
        description = "отмена записи любой стороной"
    )
    @PutMapping("/cancel")
    fun cancelAppointment(
        @RequestBody request: CancelAppointmentRequest
    ): ResponseEntity<*> =
        cancelAppointmentUseCase.execute(request).toResponseEntity()
}