package com.kndzhut.open_control.usecase.info.user

import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component


@Component
class UpdateInspectionUserInfoUseCase(
    val infoRepository: InfoRepository
) : UseCase<UpdateInspectionUserInfoRequest, EmptyResponse, UpdateInspectionUserInfoError> {
    override fun execute(request: UpdateInspectionUserInfoRequest): UseCaseResult<EmptyResponse, UpdateInspectionUserInfoError> =
        infoRepository.updateInspectionUserInfo(request).let { UseCaseResult.success(EmptyResponse()) }
}

class UpdateInspectionUserInfoRequest(
    val userId: String,
    val knoId: Int?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val surname: String?,
) : Request

class UpdateInspectionUserInfoError(
    val code: Code
) : Error {
    enum class Code {
        NO_SUCH_USER
    }
}