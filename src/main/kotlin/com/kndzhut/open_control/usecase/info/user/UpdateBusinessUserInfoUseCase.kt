package com.kndzhut.open_control.usecase.info.user

import com.kndzhut.open_control.domain.BusinessUser
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.EmptyResponse
import com.kndzhut.open_control.usecase.utils.Error
import com.kndzhut.open_control.usecase.utils.Request
import com.kndzhut.open_control.usecase.utils.UseCase
import com.kndzhut.open_control.usecase.utils.UseCaseResult
import org.springframework.stereotype.Component


@Component
class UpdateBusinessUserInfoUseCase(
    val infoRepository: InfoRepository
) : UseCase<UpdateBusinessUserInfoRequest, EmptyResponse, UpdateBusinessUserInfoError> {
    override fun execute(request: UpdateBusinessUserInfoRequest): UseCaseResult<EmptyResponse, UpdateBusinessUserInfoError>
            =
        infoRepository.getBusinessUserInfo(request.userId).updateBy(request)
            .let { infoRepository.updateBusinessUserInfo(it)
                UseCaseResult.success(EmptyResponse())
            }


    private fun BusinessUser.updateBy(request: UpdateBusinessUserInfoRequest) =
        BusinessUser(
            id = id,
            email = request.email ?: email,
            firstName = request.firstName ?: firstName,
            lastName = request.lastName ?: lastName,
            surName = request.surName ?: surName,
            inn = request.inn ?: inn,
            snils = request.snils ?: snils,
        )
}


class UpdateBusinessUserInfoRequest(
    val userId: String,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val surName: String?,
    val inn: Int?,
    val snils: Int?
): Request

class UpdateBusinessUserInfoError(
    val code: Code
): Error {
    enum class Code {
        NO_SUCH_USER
    }
}