package com.kndzhut.open_control.usecase.appointments

import com.kndzhut.open_control.domain.AppointmentTime
import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetFreeAppointmentsUseCase(
    val appointmentsRepository: AppointmentsRepository
) :
    UseCase<GetFreeAppointmentsRequest,
            GetFreeAppointmentsResponse,
            GetFreeAppointmentsError> {
    override fun execute(request: GetFreeAppointmentsRequest): UseCaseResult<GetFreeAppointmentsResponse, GetFreeAppointmentsError> =
        appointmentsRepository.getFreeWindows(request.knoId)
            .let { UseCaseResult.success(GetFreeAppointmentsResponse(it)) }
}

data class GetFreeAppointmentsRequest(
    val knoId: Int
) : Request

class GetFreeAppointmentsResponse(
    val freeWindows: List<AppointmentTime>
) : Response

class GetFreeAppointmentsError(
    val code: Code
) : Error {
    enum class Code {
        NO_FREE_WINDOWS,
        NO_SUCH_KNO_ID,
    }
}