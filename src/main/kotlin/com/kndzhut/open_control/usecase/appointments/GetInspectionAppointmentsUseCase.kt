package com.kndzhut.open_control.usecase.appointments

import com.kndzhut.open_control.domain.AppointmentInfo
import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.usecase.utils.Error
import com.kndzhut.open_control.usecase.utils.Request
import com.kndzhut.open_control.usecase.utils.Response
import com.kndzhut.open_control.usecase.utils.UseCase
import com.kndzhut.open_control.usecase.utils.UseCaseResult
import org.springframework.stereotype.Component

@Component
class GetInspectionAppointmentsUseCase(
    val appointmentsRepository: AppointmentsRepository
) :
    UseCase<GetInspectionAppointmentsRequest,
            GetInspectionAppointmentsResponse,
            GetInspectionAppointmentsError> {
    override fun execute(request: GetInspectionAppointmentsRequest): UseCaseResult<GetInspectionAppointmentsResponse, GetInspectionAppointmentsError> =
        appointmentsRepository.getInspectionAppointments(request.inspectorId)
            .let { UseCaseResult.success(GetInspectionAppointmentsResponse(it)) }
}

data class GetInspectionAppointmentsRequest(
    val inspectorId: String?
) : Request

class GetInspectionAppointmentsResponse(
    val appointments: List<AppointmentInfo>
) : Response

class GetInspectionAppointmentsError(
    val code: Code
) : Error {
    enum class Code {
        NO_APPOINTMENTS,
        NO_SUCH_USER,
    }
}