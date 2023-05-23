package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.AppointmentStartDto
import com.kndzhut.open_control.domain.AppointmentTime
import com.kndzhut.open_control.domain.Measure
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class AppointmentJDBCOperations (
    val jdbcTemplate: JdbcTemplate
) {
    fun getFreeWindows(knoId: Int): List<AppointmentTime> {
        val query = "select id, appointment_time from appointments" +
                " where status='UNSELECTED' and kno_id=$knoId"
        return jdbcTemplate.query(query) {rs, _ ->
            AppointmentTime(
                appointmentId = rs.getInt("id"),
                appointmentTime = rs.getTimestamp("appointment_time")
            )
        }
    }

    fun addAllAppointments(appointments: List<AppointmentStartDto>) {
        for (appointment in appointments) {
            val query = "insert into appointments(appointment_time, kno_id, status) " +
                    "values ('${appointment.timestamp}', ${appointment.knoId}, 'UNSELECTED')"
            jdbcTemplate.execute(query)
        }
    }
}