package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.AppointmentStartDto
import com.kndzhut.open_control.domain.AppointmentTime
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class AppointmentJDBCOperations(
    val jdbcTemplate: JdbcTemplate
) {
    fun getFreeWindows(knoId: Int): List<AppointmentTime> {
        val query = "select id, appointment_time from appointments" +
                " where status='UNSELECTED' and kno_id=$knoId"
        return jdbcTemplate.query(query) {rs, _ ->
            AppointmentTime(
                id = rs.getObject("id") as java.util.UUID,
                appointmentTime = rs.getTimestamp("appointment_time")
            )
        }
    }

    fun addAllAppointments(appointments: List<AppointmentStartDto>) {
        for (appointment in appointments) {
            val query = "insert into appointments(id, appointment_time, kno_id, status) " +
                    "values ('${appointment.id}', '${appointment.timestamp}', ${appointment.knoId}, 'UNSELECTED')" +
                    "on conflict (appointment_time, kno_id) do nothing"
            jdbcTemplate.execute(query)
        }
    }
}