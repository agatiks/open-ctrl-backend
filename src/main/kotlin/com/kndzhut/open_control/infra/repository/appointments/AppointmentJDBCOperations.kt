package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.*
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
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
        val query = "select appointments.id, appointment_time, kno.name, status from " +
                "(appointments join kno on kno.id=appointments.kno_id) " +
                "where business_id='$userId' or inspection_id='$userId'"
        return jdbcTemplate.query(query) {rs, _ ->
            AppointmentInfo(
                id = rs.getObject("id") as UUID,
                time = rs.getTimestamp("appointment_time"),
                withWho = rs.getString("name"),
                status = AppointmentStatus.valueOf(rs.getString("status"))
            )
        }
    }

    fun getInspectionAppointments(knoId: Int, userId: String?): List<AppointmentInfo> {
        val query = "select appointments.id, appointment_time, business_user_info.first_name, business_user_info.last_name, status from " +
                "(appointments join business_user_info on business_user_info.id=appointments.business_id) " +
                "where kno_id='$knoId' and (status='SELECTED' or (status='AGREED' and inspection_id='$userId'))"
        return jdbcTemplate.query(query) {rs, _ ->
            AppointmentInfo(
                id = rs.getObject("id") as UUID,
                time = rs.getTimestamp("appointment_time"),
                withWho = "${rs.getString("first_name")} ${rs.getString("last_name")}" ,
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

    fun getAppointmentInfo(appointmentId: UUID): AppointmentDto {
        val query = "select * from appointments where id='$appointmentId'"
        return jdbcTemplate.query(query) { rs, _ ->
            AppointmentDto(
                id = rs.getObject("id") as UUID,
                businessId = rs.getString("business_id"),
                inspectionId = rs.getString("inspection_id"),
                time = rs.getTimestamp("appointment_time"),
                status = AppointmentStatus.valueOf(rs.getString("status")),
                kno = infoRepository.getKno(rs.getInt("kno_id")),
                measure = infoRepository.getMeasure(rs.getInt("measure_id")),
                description = rs.getString("description"),
                files = null
            )
        }[0]
    }

    fun getAppointmentInfoDirty(appointmentId: UUID): Appointment {
        val query = "select * from appointments where id='$appointmentId'"
        return jdbcTemplate.query(query) { rs, _ ->
            Appointment(
                id = rs.getObject("id") as UUID,
                businessId = rs.getString("business_id"),
                inspectionId = rs.getString("inspection_id"),
                time = rs.getTimestamp("appointment_time"),
                status = AppointmentStatus.valueOf(rs.getString("status")),
                knoId = rs.getInt("kno_id"),
                measureId = rs.getInt("measure_id"),
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