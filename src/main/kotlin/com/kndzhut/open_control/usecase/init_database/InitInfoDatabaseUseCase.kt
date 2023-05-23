package com.kndzhut.open_control.usecase.init_database

import com.kndzhut.open_control.domain.Kno
import com.kndzhut.open_control.infra.clients.google_sheets.GoogleSheetsClient
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import org.springframework.stereotype.Component

@Component
class InitInfoDatabaseUseCase(
    private val infoRepository: InfoRepository,
    private val googleSheetsClient: GoogleSheetsClient
) {
    fun execute() {
        val knoInfo = googleSheetsClient.getKnoList()
        knoInfo.map { Kno(it.first, it.second) }
            .let { infoRepository.addAllKno(it) }
        val measures = googleSheetsClient.getMeasuresList()
        infoRepository.addAllMeasures(measures)
    }
}