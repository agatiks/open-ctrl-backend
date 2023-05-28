package com.kndzhut.open_control.usecase.users

import com.kndzhut.open_control.domain.InspectionUser
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetInspectionUserInfoUseCase(
    val infoRepository: InfoRepository
) : UseCase<GetInspectionUserInfoRequest, GetInspectionUserInfoResponse, GetInspectionUserInfoError> {
    override fun execute(request: GetInspectionUserInfoRequest): UseCaseResult<GetInspectionUserInfoResponse, GetInspectionUserInfoError> =
        infoRepository.getInspectionUserInfo(request.userId)
            .let { UseCaseResult.success(GetInspectionUserInfoResponse(it)) }
}

class GetInspectionUserInfoRequest(
    val userId: String,
) : Request

class GetInspectionUserInfoResponse(
    val user: InspectionUser
) : Response

class GetInspectionUserInfoError(
    val code: Code
) : Error {
    enum class Code {
        NO_SUCH_USER
    }
}