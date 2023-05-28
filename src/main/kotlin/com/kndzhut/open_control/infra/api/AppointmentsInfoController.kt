package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.appointments_info.GetAppointmentInfoRequest
import com.kndzhut.open_control.usecase.appointments_info.GetAppointmentInfoUseCase
import com.kndzhut.open_control.usecase.appointments_info.UpdateAppointmentInfoRequest
import com.kndzhut.open_control.usecase.appointments_info.UpdateAppointmentInfoUseCase
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(
    value = ["/appointment-info"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Appointments API", description = "API для работы с дополнительной информацией о записях")
class AppointmentsInfoController(
    private val getAppointmentInfoUseCase: GetAppointmentInfoUseCase,
    private val updateAppointmentInfoUseCase: UpdateAppointmentInfoUseCase,

    /*private val uploadAppointmentFileUseCase: UploadAppointmentFileUseCase,
    private val downloadAppointmentFileUseCase: DownloadAppointmentFileUseCase,
    private val getListOfAppointmentFiles: GetListOfAppointmentFiles*/
) {
    @Operation(
        method = "GET",
        description = "получение информации о записи"
    )
    @GetMapping()
    fun getAppointmentInfo(
        @Schema(description = "id записи") @RequestParam appointmentId: UUID
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