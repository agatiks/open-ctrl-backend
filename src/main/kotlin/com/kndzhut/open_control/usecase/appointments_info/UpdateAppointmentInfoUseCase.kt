package com.kndzhut.open_control.usecase.appointments_info

import com.kndzhut.open_control.domain.Appointment
import com.kndzhut.open_control.domain.AppointmentMutable
import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class UpdateAppointmentInfoUseCase(
    val appointmentsRepository: AppointmentsRepository
) : UseCase<UpdateAppointmentInfoRequest, EmptyResponse, UpdateAppointmentInfoError> {
    override fun execute(request: UpdateAppointmentInfoRequest): UseCaseResult<EmptyResponse, UpdateAppointmentInfoError> =
        appointmentsRepository.getAppointmentInfo(request.appointmentId).updateBy(request)
            .let {
                appointmentsRepository.updateAppointmentInfo(it)
                UseCaseResult.success(EmptyResponse())
            }

    private fun Appointment.updateBy(request: UpdateAppointmentInfoRequest) =
        AppointmentMutable(
            id = id,
            measureId = request.measureId ?: measureId,
            description = request.description ?: description
        )
}

class UpdateAppointmentInfoRequest(
    val appointmentId: UUID,
    val measureId: Int?,
    val description: String?
) : Request

class UpdateAppointmentInfoError(
    val code: Code
) : Error {
    enum class Code {
        NO_SUCH_USER
    }
}