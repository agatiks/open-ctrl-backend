package com.kndzhut.open_control.domain

import org.springframework.web.multipart.MultipartFile
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
    val withWho: String
)

class Appointment(
    val id: UUID,
    val time: Timestamp,
    val knoId: Int,
    val businessId: String?,
    val inspectionId: String?,
    val measureId: Int?,
    val description: String?,
    val files: List<MultipartFile>?,
    val status: AppointmentStatus
)

class AppointmentMutable(
    val id: UUID,
    //val inspectionId: String?,
    val measureId: Int?,
    val description: String?
)