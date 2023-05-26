package com.kndzhut.open_control.usecase.info.user

import com.kndzhut.open_control.domain.Role
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class CreateUserUseCase(
    val infoRepository: InfoRepository
) : UseCase<CreateUserRequest, EmptyResponse, CreateUserError> {
    override fun execute(request: CreateUserRequest): UseCaseResult<EmptyResponse, CreateUserError> =
        infoRepository.createUser(request.userId, request.role).let { UseCaseResult.success(EmptyResponse()) }
}

class CreateUserRequest(
    val userId: String,
    val role: Role
) : Request

class CreateUserError(
    val code: Code
) : Error {
    enum class Code {
        USER_ALREADY_EXISTS
    }
}

