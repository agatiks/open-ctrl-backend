package com.kndzhut.open_control.usecase.users

import com.kndzhut.open_control.domain.BusinessUser
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetBusinessUserInfoUseCase(
    val infoRepository: InfoRepository
) : UseCase<GetBusinessUserInfoRequest, GetBusinessUserInfoResponse, GetBusinessUserInfoError> {
    override fun execute(request: GetBusinessUserInfoRequest): UseCaseResult<GetBusinessUserInfoResponse, GetBusinessUserInfoError>
            = infoRepository.getBusinessUserInfo(request.userId)
        .let { UseCaseResult.success(GetBusinessUserInfoResponse(it)) }
}

class GetBusinessUserInfoRequest(
    val userId: String,
): Request

class GetBusinessUserInfoResponse(
    val user: BusinessUser
): Response

class GetBusinessUserInfoError(
    val code: Code
): Error {
    enum class Code {
        NO_SUCH_USER
    }
}