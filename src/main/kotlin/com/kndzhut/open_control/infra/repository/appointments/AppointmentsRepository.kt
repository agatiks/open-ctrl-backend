package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.AppointmentInfo
import com.kndzhut.open_control.domain.AppointmentStartDto
import com.kndzhut.open_control.domain.AppointmentTime
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AppointmentsRepository(
    val appointmentJDBCOperations: AppointmentJDBCOperations
) {
    fun getFreeWindows(knoId: Int): List<AppointmentTime> =
        appointmentJDBCOperations.getFreeWindows(knoId)

    fun addAllAppointments(appointments: List<AppointmentStartDto>) =
        appointmentJDBCOperations.addAllAppointments(appointments)

    fun getUserAppointments(userId: String): List<AppointmentInfo> =
        appointmentJDBCOperations.getUserAppointments(userId)

    fun selectAppointment(userId: String, appointmentId: UUID, measureId: Int) =
        appointmentJDBCOperations.selectAppointment(userId, appointmentId, measureId)

    fun approveAppointment(userId: String, appointmentId: UUID) =
        appointmentJDBCOperations.approveAppointment(userId, appointmentId)

    fun cancelAppointment(appointmentId: UUID) =
        appointmentJDBCOperations.cancelAppointment(appointmentId)

    fun isNotSelected(appointmentId: UUID): Boolean =
        appointmentJDBCOperations.isNotSelected(appointmentId)

    fun areKnoEqual(userId: String, appointmentId: UUID): Boolean =
        appointmentJDBCOperations.areKnoEqual(userId, appointmentId)
}