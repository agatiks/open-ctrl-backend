package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.AppointmentInfo
import com.kndzhut.open_control.domain.AppointmentStartDto
import com.kndzhut.open_control.domain.AppointmentStatus
import com.kndzhut.open_control.domain.AppointmentTime
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AppointmentJDBCOperations(
    val jdbcTemplate: JdbcTemplate
) {
    fun getFreeWindows(knoId: Int): List<AppointmentTime> {
        val query = "select id, appointment_time from appointments" +
                " where status='UNSELECTED' and kno_id=$knoId"
        return jdbcTemplate.query(query) {rs, _ ->
            AppointmentTime(
                id = rs.getObject("id") as UUID,
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

    fun getUserAppointments(userId: String): List<AppointmentInfo> {
        val query = "select appointments.id, appointment_time, kno.name, status from " +
                "(appointments join kno on kno.id=appointments.kno_id) " +
                "where business_id='$userId' or inspection_id='$userId'"
        return jdbcTemplate.query(query) {rs, _ ->
            AppointmentInfo(
                id = rs.getObject("id") as UUID,
                time = rs.getTimestamp("appointment_time"),
                kno = rs.getString("name"),
                status = AppointmentStatus.valueOf(rs.getString("status"))
            )
        }
    }

    fun isNotSelected(appointmentId: UUID): Boolean {
        val selectQuery = "select status from appointments where id='$appointmentId'"
        val status = jdbcTemplate.query(selectQuery) {rs, _ -> AppointmentStatus.valueOf(rs.getString("status")) }[0]
        return status == AppointmentStatus.UNSELECTED
    }

    fun selectAppointment(userId: String, appointmentId: UUID, measureId: Int) {
        val query = "update appointments " +
                "set business_id='$userId', status='SELECTED', measure_id=$measureId " +
                "where id='$appointmentId'"
        jdbcTemplate.execute(query)
    }

    fun areKnoEqual(userId: String, appointmentId: UUID): Boolean {
        val userQuery = "select kno_id from inspector_user_info where id='$userId'"
        val appointmentQuery = "select kno_id from appointments where id='$appointmentId'"
        val userKno = jdbcTemplate.query(userQuery) {rs, _ -> rs.getInt("kno_id") }[0]
        val appointmentKno = jdbcTemplate.query(appointmentQuery) {rs, _ -> rs.getInt("kno_id") }[0]
        return userKno == appointmentKno
    }

    fun approveAppointment(userId: String, appointmentId: UUID) {
        val query = "update appointments " +
                "set inspection_id='$userId', status='AGREED' " +
                "where id='$appointmentId'"
        jdbcTemplate.execute(query)
    }

    fun cancelAppointment(appointmentId: UUID) {
        val query = "update appointments " +
                "set inspection_id=null, business_id=null, measure_id=null, status='UNSELECTED' " +
                "where id='$appointmentId'"
        jdbcTemplate.execute(query)
    }


}