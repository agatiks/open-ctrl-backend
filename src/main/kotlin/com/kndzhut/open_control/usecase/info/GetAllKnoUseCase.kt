package com.kndzhut.open_control.usecase.info

import com.kndzhut.open_control.domain.Kno
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetAllKnoUseCase(
    private val infoRepository: InfoRepository
): UseCase<EmptyRequest, GetAllKNOResponse, EmptyError> {
    override fun execute(request: EmptyRequest): UseCaseResult<GetAllKNOResponse, EmptyError>
        = infoRepository.getAllKno().let { UseCaseResult.success(GetAllKNOResponse(it)) }
}

class GetAllKNOResponse(
     val knoList: List<Kno>
): Response
