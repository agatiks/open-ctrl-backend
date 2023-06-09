package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.*
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.UUID

@Component
class AppointmentJDBCOperations(
    val jdbcTemplate: JdbcTemplate,
    val infoRepository: InfoRepository
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
        val query = "select id, appointment_time, status, kno_id, measure_id from appointments " +
                "where business_id='$userId' or inspection_id='$userId'"
        return jdbcTemplate.query(query) {rs, _ ->
            BusinessAppointmentInfo(
                id = rs.getObject("id") as UUID,
                time = rs.getTimestamp("appointment_time"),
                knoId = rs.getInt("kno_id"),
                knoName = infoRepository.getKno(rs.getInt("kno_id")),
                measureId = rs.getInt("measure_id"),
                measureName = infoRepository.getKno(rs.getInt("measure_id")),
                status = AppointmentStatus.valueOf(rs.getString("status"))
            )
        }
    }

    fun getInspectionAppointments(userId: String?): List<AppointmentInfo> {
        val inspectionQuery = "select kno_id from inspection_user_info where id='$userId'"
        val knoId = jdbcTemplate.query(inspectionQuery) {rs, _ -> rs.getInt("kno_id")}.firstOrNull()
            ?: throw IllegalStateException("No such user")
        val query = "select id, appointment_time, business_id, status from appointments " +
                "where kno_id=$knoId and (status='SELECTED' or (status='AGREED' and inspection_id='$userId'))"

        val apps = jdbcTemplate.query(query) {rs, _ ->
            InspectionAppointmentInfo(
                id = rs.getObject("id") as UUID,
                time = rs.getTimestamp("appointment_time"),
                businessUserId = rs.getString("business_id"),
                status = AppointmentStatus.valueOf(rs.getString("status"))
            )
        }
        return apps
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
        val userQuery = "select kno_id from inspection_user_info where id='$userId'"
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

    fun getAppointmentInfo(appointmentId: UUID): Appointment {
        val query = "select * from appointments where id='$appointmentId'"
        return jdbcTemplate.query(query) { rs, _ ->
            Appointment(
                id = rs.getObject("id") as UUID,
                businessId = rs.getString("business_id"),
                inspectionId = rs.getString("inspection_id"),
                time = rs.getTimestamp("appointment_time"),
                status = AppointmentStatus.valueOf(rs.getString("status")),
                knoId = rs.getInt("kno_id"),
                knoName = infoRepository.getKno(rs.getInt("kno_id")),
                measureId = rs.getInt("measure_id"),
                measureName = infoRepository.getKno(rs.getInt("measure_id")),
                description = rs.getString("description"),
                files = null
            )
        }[0]
    }

    fun updateAppointmentInfo(app: AppointmentMutable) {
        val query = with(app) {"update appointments " +
                "set measure_id='$measureId', description='$description' " +
                "where id='$id'"}
        jdbcTemplate.execute(query)
    }


}