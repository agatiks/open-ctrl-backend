package com.kndzhut.open_control.domain

import java.sql.Timestamp
import java.util.*

class AppointmentStartDto(
    val id: UUID,
    val timestamp: Timestamp,
    val knoId: Int
)

class AppointmentTime(
    val id: UUID,
    val appointmentTime: Timestamp
)

enum class AppointmentStatus {
    UNSELECTED, SELECTED, AGREED
}

class AppointmentInfo(
    val id: UUID,
    val time: Timestamp,
    val status: AppointmentStatus,
    val kno: String
)