package com.kndzhut.open_control.usecase.appointments

import com.kndzhut.open_control.domain.AppointmentInfo
import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetUserAppointmentsUseCase(
    val appointmentsRepository: AppointmentsRepository
) :
    UseCase<GetUserAppointmentsRequest,
            GetUserAppointmentsResponse,
            GetUserAppointmentsError> {
    override fun execute(request: GetUserAppointmentsRequest): UseCaseResult<GetUserAppointmentsResponse, GetUserAppointmentsError> =
        appointmentsRepository.getUserAppointments(request.userId)
            .let { UseCaseResult.success(GetUserAppointmentsResponse(it)) }
}

data class GetUserAppointmentsRequest(
    val userId: String
) : Request

class GetUserAppointmentsResponse(
    val appointments: List<AppointmentInfo>
) : Response

class GetUserAppointmentsError(
    val code: Code
) : Error {
    enum class Code {
        NO_APPOINTMENTS,
        NO_SUCH_USER,
    }
}


