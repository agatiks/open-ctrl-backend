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

open class AppointmentInfo(
    open val id: UUID,
    open val time: Timestamp,
    open val status: AppointmentStatus,
)

class BusinessAppointmentInfo(
    override val id: UUID,
    override val time: Timestamp,
    override val status: AppointmentStatus,
    val knoId: Int,
    val knoName: String,
    val measureId: Int?,
    val measureName: String?
): AppointmentInfo(id, time, status)

class InspectionAppointmentInfo(
    override val id: UUID,
    override val time: Timestamp,
    override val status: AppointmentStatus,
    val businessUserId: String,
    //val businessUserName: Int,
): AppointmentInfo(id, time, status)

class AppointmentDto(
    val id: UUID,
    val time: Timestamp,
    val kno: String,
    val businessId: String?,
    val inspectionId: String?,
    val measure: String?,
    val description: String?,
    val files: List<MultipartFile>?,
    val status: AppointmentStatus
)

class Appointment(
    val id: UUID,
    val time: Timestamp,
    val knoId: Int,
    val knoName: String,
    val businessId: String?,
    val inspectionId: String?,
    val measureId: Int?,
    val measureName: String?,
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