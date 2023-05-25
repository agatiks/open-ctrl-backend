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