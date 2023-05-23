package com.kndzhut.open_control.usecase.init_database

import com.kndzhut.open_control.domain.Kno
import com.kndzhut.open_control.infra.clients.google_sheets.GoogleSheetsClient
import com.kndzhut.open_control.infra.repository.appointments.AppointmentsRepository
import com.kndzhut.open_control.infra.repository.info.InfoRepository
import org.springframework.stereotype.Component

@Component
class InitAppointmentsDatabaseUseCase (private val appointmentsRepository: AppointmentsRepository,
                                       private val googleSheetsClient: GoogleSheetsClient
) {
    fun execute() {
        val appointments = googleSheetsClient.getAppointments()
        appointmentsRepository.addAllAppointments(appointments)
    }
}