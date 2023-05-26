package com.kndzhut.open_control.usecase.appointments

import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.EmptyResponse
import com.kndzhut.open_control.usecase.utils.Error
import com.kndzhut.open_control.usecase.utils.Request
import com.kndzhut.open_control.usecase.utils.UseCase
import com.kndzhut.open_control.usecase.utils.UseCaseResult
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApproveAppointmentUseCase(
    val appointmentsRepository: AppointmentsRepository
) :
    UseCase<ApproveAppointmentRequest,
            EmptyResponse,
            ApproveAppointmentError> {

    override fun execute(request: ApproveAppointmentRequest): UseCaseResult<EmptyResponse, ApproveAppointmentError> =
        request.takeIf { appointmentsRepository.areKnoEqual(request.userId, request.appointmentId) }
            ?.let {
                appointmentsRepository.approveAppointment(request.userId, request.appointmentId)
                UseCaseResult.success(EmptyResponse())
            } ?: UseCaseResult.error(ApproveAppointmentError(ApproveAppointmentError.Code.NOT_YOUR_KNO))
}

data class ApproveAppointmentRequest(
    val userId: String,
    val appointmentId: UUID
) : Request
class ApproveAppointmentError (
    val code: Code
) : Error {
    enum class Code {
        NOT_YOUR_KNO,
        NO_SUCH_USER,
    }
}