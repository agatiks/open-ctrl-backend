package com.kndzhut.open_control.usecase.info.kno

import com.kndzhut.open_control.domain.Kno
import com.kndzhut.open_control.domain.Measure
import com.kndzhut.open_control.domain.MeasureDto
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import com.kndzhut.open_control.usecase.utils.*
import org.springframework.stereotype.Component

@Component
class GetMeasuresByKnoUseCase(
    val infoRepository: InfoRepository
) : UseCase<GetMeasuresByKnoRequest, GetMeasuresByKnoResponse, EmptyError> {
    override fun execute(request: GetMeasuresByKnoRequest): UseCaseResult<GetMeasuresByKnoResponse, EmptyError>
            = infoRepository.getAllMeasuresByKno(request.knoId).let { UseCaseResult.success(GetMeasuresByKnoResponse(it)) }
}

class GetMeasuresByKnoRequest(
    val knoId: Int
): Request

class GetMeasuresByKnoResponse(
    val measures: List<MeasureDto>
): Response