package com.kndzhut.open_control.usecase.users

import com.kndzhut.open_control.domain.InspectionUser
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component


@Component
class UpdateInspectionUserInfoUseCase(
    val infoRepository: InfoRepository
) : UseCase<UpdateInspectionUserInfoRequest, EmptyResponse, UpdateInspectionUserInfoError> {
    override fun execute(request: UpdateInspectionUserInfoRequest): UseCaseResult<EmptyResponse, UpdateInspectionUserInfoError> =
        infoRepository.getInspectionUserInfo(request.userId).updateBy(request)
            .let {
                infoRepository.updateInspectionUserInfo(it)
                UseCaseResult.success(EmptyResponse())
            }

    private fun InspectionUser.updateBy(request: UpdateInspectionUserInfoRequest) =
        InspectionUser(
            id = id,
            email = request.email ?: email,
            firstName = request.firstName ?: firstName,
            lastName = request.lastName ?: lastName,
            surName = request.surName ?: surName,
            knoId = request.knoId ?: knoId
        )
}

class UpdateInspectionUserInfoRequest(
    val userId: String,
    val knoId: Int?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val surName: String?,
) : Request

class UpdateInspectionUserInfoError(
    val code: Code
) : Error {
    enum class Code {
        NO_SUCH_USER
    }
}