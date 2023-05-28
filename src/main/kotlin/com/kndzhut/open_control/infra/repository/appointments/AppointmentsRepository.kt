package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.*
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

    fun getInspectionAppointments(knoId: Int, inspectorId: String?): List<AppointmentInfo> =
        appointmentJDBCOperations.getInspectionAppointments(knoId, inspectorId)

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

    fun getAppointmentInfo(appointmentId: UUID): Appointment =
        appointmentJDBCOperations.getAppointmentInfo(appointmentId)

    fun updateAppointmentInfo(app: AppointmentMutable) =
        appointmentJDBCOperations.updateAppointmentInfo(app)
}