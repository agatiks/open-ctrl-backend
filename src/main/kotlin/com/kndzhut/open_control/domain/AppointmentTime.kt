package com.kndzhut.open_control.domain

import java.sql.Timestamp
class AppointmentStartDto(
    val timestamp: Timestamp,
    val knoId: Int
)
class AppointmentTime (
    val appointmentId: Int,
    val appointmentTime: Timestamp
)