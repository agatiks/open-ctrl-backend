package com.kndzhut.open_control.infra.api


import com.kndzhut.open_control.usecase.appointments.*
import com.kndzhut.open_control.usecase.appointments_info.GetAppointmentInfoRequest
import com.kndzhut.open_control.usecase.appointments_info.GetAppointmentInfoUseCase
import com.kndzhut.open_control.usecase.appointments_info.UpdateAppointmentInfoRequest
import com.kndzhut.open_control.usecase.appointments_info.UpdateAppointmentInfoUseCase
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
    private val cancelAppointmentUseCase: CancelAppointmentUseCase,
    private val getInspectionAppointmentsUseCase: GetInspectionAppointmentsUseCase,
    private val getAppointmentInfoUseCase: GetAppointmentInfoUseCase,
    private val updateAppointmentInfoUseCase: UpdateAppointmentInfoUseCase
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
        description = "получение списка всех записей бизнес-пользователя"
    )
    @GetMapping("/business")
    fun getUserAppointments(
        @RequestParam userId: String
    ): ResponseEntity<*> {
        val request = GetUserAppointmentsRequest(userId)
        return getUserAppointmentsUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "GET",
        description = "получение списка всех записей инспекции"
    )
    @GetMapping("/inspection")
    fun getInspectionAppointments(
        @RequestParam knoId: Int,
        @RequestParam inspectorId: String
    ): ResponseEntity<*> {
        val request = GetInspectionAppointmentsRequest(knoId, inspectorId)
        return getInspectionAppointmentsUseCase.execute(request).toResponseEntity()
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

    @Operation(
        method = "GET",
        description = "получение информации о записи"
    )
    @GetMapping()
    fun getAppointmentInfo(
        @RequestParam appointmentId: UUID
    ): ResponseEntity<*> {
        val request = GetAppointmentInfoRequest(appointmentId)
        return getAppointmentInfoUseCase.execute(request).toResponseEntity()
    }

    @Operation(
        method = "PUT",
        description = "обновление информации о записи"
    )
    @PutMapping()
    fun updateAppointmentInfo(
        @RequestBody request: UpdateAppointmentInfoRequest
    ): ResponseEntity<*> =
        updateAppointmentInfoUseCase.execute(request).toResponseEntity()

    /*@Operation(
        method = "POST",
        description = "загрузка файла"
    )
    @PostMapping("/file/upload")
    fun uploadFile(
        @RequestParam file: MultipartFile
    ): ResponseEntity<*> =
        uploadFileUseCase.execute(request).toResponseEntity()*/
}