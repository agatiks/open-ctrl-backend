package com.kndzhut.open_control.infra.repository.info

import com.kndzhut.open_control.domain.Kno
import com.kndzhut.open_control.domain.Measure
import com.kndzhut.open_control.domain.MeasureDto
import org.springframework.stereotype.Repository

@Repository
class InfoRepository (
    private val infoJDBCOperations: InfoJDBCOperations
){
    fun addAllKno(knoList: List<Kno>) = infoJDBCOperations.addAllKNO(knoList)
    fun getAllKno(): List<Kno> = infoJDBCOperations.getAllKNO()
    fun addAllMeasures(measures: List<Measure>) = infoJDBCOperations.addAllMeasures(measures)
    fun getAllMeasuresByKno(knoId: Int): List<MeasureDto> = infoJDBCOperations.getAllMeasuresByKno(knoId)
}