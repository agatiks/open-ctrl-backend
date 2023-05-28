package com.kndzhut.open_control.usecase.appointments_info

import com.kndzhut.open_control.domain.Appointment
import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetAppointmentInfoUseCase(
    val appointmentsRepository: AppointmentsRepository
) : UseCase<GetAppointmentInfoRequest, GetAppointmentInfoResponse, GetAppointmentInfoError> {
    override fun execute(request: GetAppointmentInfoRequest): UseCaseResult<GetAppointmentInfoResponse, GetAppointmentInfoError> =
        appointmentsRepository.getAppointmentInfo(request.appointmentId)
            .let { UseCaseResult.success(GetAppointmentInfoResponse(it)) }
}

class GetAppointmentInfoRequest(
    val appointmentId: UUID,
) : Request

class GetAppointmentInfoResponse(
    val appointment: Appointment
) : Response

class GetAppointmentInfoError(
    val code: Code
) : Error {
    enum class Code {
        NO_SUCH_USER
    }
}