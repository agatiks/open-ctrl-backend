package com.kndzhut.open_control.usecase.users.business

import com.kndzhut.open_control.domain.Business
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetBusinessUseCase(
    val infoRepository: InfoRepository
) : UseCase<GetBusinessRequest, GetBusinessResponse, GetBusinessError> {
    override fun execute(request: GetBusinessRequest): UseCaseResult<GetBusinessResponse, GetBusinessError> =
        infoRepository.getBusiness(request.businessId).let { UseCaseResult.success(GetBusinessResponse(it)) }
}

class GetBusinessRequest(
    val businessId: UUID
) : Request

class GetBusinessResponse(
    val business: Business
) : Response

class GetBusinessError(
    val code: Code
) : Error {
    enum class Code {
        BUSINESS_ALREADY_EXISTS
    }
}