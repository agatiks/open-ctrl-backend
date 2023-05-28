package com.kndzhut.open_control.usecase.users

import com.kndzhut.open_control.domain.Role
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetUserRoleUseCase(
    val infoRepository: InfoRepository
) : UseCase<GetUserRoleRequest, GetUserRoleResponse, GetUserRoleError> {
    override fun execute(request: GetUserRoleRequest): UseCaseResult<GetUserRoleResponse, GetUserRoleError> =
        infoRepository.getUserRoleInfo(request.userId)
            .let { UseCaseResult.success(GetUserRoleResponse(it)) }
}

class GetUserRoleRequest(
    val userId: String,
) : Request

class GetUserRoleResponse(
    val role: Role
) : Response

class GetUserRoleError(
    val code: Code
) : Error {
    enum class Code {
        NO_SUCH_USER
    }
}