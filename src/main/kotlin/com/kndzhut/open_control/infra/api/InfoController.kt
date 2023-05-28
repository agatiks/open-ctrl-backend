package com.kndzhut.open_control.infra.api

import com.kndzhut.open_control.usecase.info.GetAllKnoUseCase
import com.kndzhut.open_control.usecase.info.GetMeasuresByKnoRequest
import com.kndzhut.open_control.usecase.info.GetMeasuresByKnoUseCase
import com.kndzhut.open_control.usecase.init_database.InitInfoDatabaseUseCase
import com.kndzhut.open_control.usecase.utils.EmptyRequest
import com.kndzhut.open_control.usecase.utils.toResponseEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.PostConstruct
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(
    value = ["/info"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Info API", description = "API для работы с дополнительной информацией")
class InfoController(
    private val initDatabaseUseCase: InitInfoDatabaseUseCase,
    private val getAllKnoUseCase: GetAllKnoUseCase,
    private val getAllMeasuresByKnoUseCase: GetMeasuresByKnoUseCase,
    //private val getKnoByIdUseCase: GetAllKnoUseCase,
    //private val getMeasureByIdUseCase: GetMeasuresByKnoUseCase,
) {
    @PostConstruct
    fun getInfoFromGoogleSheets() {
        initDatabaseUseCase.execute()
    }

    @Operation(
        method = "GET", description = "получение списка всех доступных КНО"
    )
    @GetMapping("/knos")
    fun getFreeAppointments(): ResponseEntity<*> =
        getAllKnoUseCase.execute(request = EmptyRequest()).toResponseEntity()

    @Operation(
        method = "GET", description = "получение списка всех доступных видов контроля"
    )
    @GetMapping("/measures")
    fun getFreeAppointments(@RequestParam knoId: Int): ResponseEntity<*> {
        val request = GetMeasuresByKnoRequest(knoId)
        return getAllMeasuresByKnoUseCase.execute(request).toResponseEntity()
    }
}