package com.kndzhut.open_control.infra.repository.info

import com.kndzhut.open_control.domain.Kno
import com.kndzhut.open_control.domain.Measure
import com.kndzhut.open_control.domain.MeasureDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class InfoJDBCOperations(
    val jdbcTemplate: JdbcTemplate
) {
    fun addAllKNO(knoList: List<Kno>) {
        for (kno in knoList) {
            val query = "insert into kno values (${kno.id}, '${kno.name}') " +
                    "on conflict (id) do update set name='${kno.name}'"
            jdbcTemplate.execute(query)
        }
    }

    fun getAllKNO(): List<Kno> {
        val query = "select * from kno"
        return jdbcTemplate.query(query) { rs, _ ->
            Kno(rs.getInt("id"), rs.getString("name"))
        }
    }

    fun addAllMeasures(measures: List<Measure>) {
        for (measure in measures) {
            val query = "insert into measures values (${measure.id}, '${measure.name}', '${measure.knoId}') " +
                    "on conflict (id) do update set name='${measure.name}', kno_id='${measure.knoId}'"
            jdbcTemplate.execute(query)
        }
    }

    fun getAllMeasuresByKno(knoId: Int): MutableList<MeasureDto> {
        val query = "select id, name from measures where kno_id=$knoId"
        return jdbcTemplate.query(query) { rs, _ ->
            MeasureDto(
                id = rs.getInt("id"),
                name = rs.getString("name"))
        }
    }
}

