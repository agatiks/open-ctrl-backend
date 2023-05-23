package com.kndzhut.open_control.infra.repository.appointments

import com.kndzhut.open_control.domain.AppointmentStartDto
import com.kndzhut.open_control.domain.AppointmentTime
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class AppointmentsRepository(
    val appointmentJDBCOperations: AppointmentJDBCOperations
){
    fun getFreeWindows(knoId: Int): List<AppointmentTime> =
        appointmentJDBCOperations.getFreeWindows(knoId)
    fun addAllAppointments(appointments: List<AppointmentStartDto>) =
        appointmentJDBCOperations.addAllAppointments(appointments)
}