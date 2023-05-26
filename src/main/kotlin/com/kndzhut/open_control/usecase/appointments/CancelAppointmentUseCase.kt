package com.kndzhut.open_control.usecase.appointments

import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class CancelAppointmentUseCase(
    val appointmentsRepository: AppointmentsRepository
) :
    UseCase<CancelAppointmentRequest,
            EmptyResponse,
            EmptyError> {

    override fun execute(request: CancelAppointmentRequest): UseCaseResult<EmptyResponse, EmptyError> =
        appointmentsRepository.cancelAppointment(request.appointmentId)
            .let { UseCaseResult.success(EmptyResponse()) }


}

data class CancelAppointmentRequest(
    val appointmentId: UUID
) : Request
