package com.kndzhut.open_control.usecase.info.business

import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateBusinessUseCase(
    val infoRepository: InfoRepository
) : UseCase<CreateBusinessRequest, CreateBusinessResponse, CreateBusinessError> {
    override fun execute(request: CreateBusinessRequest): UseCaseResult<CreateBusinessResponse, CreateBusinessError> =
        infoRepository.createBusiness(request).let { UseCaseResult.success(CreateBusinessResponse(it)) }
}

class CreateBusinessRequest(
    val userId: String,
    val type: String,
    val kind: String,
    val objectClass: String,
    val activityClass: String,
    val address: String
) : Request

class CreateBusinessResponse(
    val businessId: UUID
) : Response

class CreateBusinessError(
    val code: Code
) : Error {
    enum class Code {
        BUSINESS_ALREADY_EXISTS
    }
}