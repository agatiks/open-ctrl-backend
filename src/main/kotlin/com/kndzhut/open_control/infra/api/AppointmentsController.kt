package com.kndzhut.open_control.infra.api


import com.kndzhut.open_control.usecase.appointments.get_free_appointments.GetFreeAppointmentsRequest
import com.kndzhut.open_control.usecase.appointments.get_free_appointments.GetFreeAppointmentsUseCase
import com.kndzhut.open_control.usecase.init_database.InitAppointmentsDatabaseUseCase
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.aspectj.lang.annotation.After
import org.springframework.context.annotation.DependsOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RestController
@DependsOn("infoController")
@RequestMapping(
    value = ["/appointments"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Appointments API", description = "API для работы со встречами")
class AppointmentsController (
    private val initAppointmentsDatabaseUseCase: InitAppointmentsDatabaseUseCase,
    private val getFreeAppointmentsUseCase: GetFreeAppointmentsUseCase,
) {
    @PostConstruct
    fun getAppointmentsFromGoogleSheets() {
        initAppointmentsDatabaseUseCase.execute()
    }

    @Operation(
        method = "GET", description = "получение списка всех доступных окон, начиная с времени запроса", responses = [
            ApiResponse(description = "успешное создание коммуникации", responseCode = "200"),
        ]
    )
    @GetMapping("/free")
    fun getFreeAppointments(
        @RequestParam knoId: Int
    ): ResponseEntity<*> {
        val request = GetFreeAppointmentsRequest(knoId)
        val response = getFreeAppointmentsUseCase.execute(request).toResponseEntity()
        return response
    }
}