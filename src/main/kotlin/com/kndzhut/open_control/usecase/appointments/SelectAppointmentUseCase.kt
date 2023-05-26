package com.kndzhut.open_control.usecase.appointments

import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class SelectAppointmentUseCase(
    val appointmentsRepository: AppointmentsRepository
) :
    UseCase<SelectAppointmentRequest,
            EmptyResponse,
            SelectAppointmentError> {

    override fun execute(request: SelectAppointmentRequest): UseCaseResult<EmptyResponse, SelectAppointmentError> =
        request.takeIf { appointmentsRepository.isNotSelected(request.appointmentId) }
            ?.let {
                appointmentsRepository.selectAppointment(request.userId, request.appointmentId, request.measureId)
                UseCaseResult.success(EmptyResponse())
            } ?: UseCaseResult.error(SelectAppointmentError(SelectAppointmentError.Code.ALREADY_SELECTED))
}

data class SelectAppointmentRequest(
    val userId: String,
    val appointmentId: UUID,
    val measureId: Int
) : Request

class SelectAppointmentError(
    val code: Code
) : Error {
    enum class Code {
        ALREADY_SELECTED,
        NO_SUCH_USER,
    }
}


