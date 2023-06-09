package com.kndzhut.open_control.usecase.users

import com.kndzhut.open_control.domain.Role
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class RegistryUserUseCase(
    val infoRepository: InfoRepository
) : UseCase<RegistryUserRequest, RegistryUserResponse, RegistryUserError> {
    override fun execute(request: RegistryUserRequest): UseCaseResult<RegistryUserResponse, RegistryUserError> =
        infoRepository.registryUser(request.login, request.role, request.password)
            .let { UseCaseResult.success(RegistryUserResponse(it)) }
}

class RegistryUserRequest(
    val login: String,
    val role: Role,
    val password: String
) : Request

class RegistryUserResponse(
    val uid: String
) : Response

class RegistryUserError(
    val code: Code
) : Error {
    enum class Code {
        USER_ALREADY_EXISTS
    }
}

