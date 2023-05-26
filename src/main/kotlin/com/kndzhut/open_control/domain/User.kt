package com.kndzhut.open_control.domain

class BusinessUser(
    val id: String,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val surName: String?,
    val inn: Int?,
    val snils: Int?
)

class InspectionUser(
    val id: String,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val surName: String?,
    val knoId: Int?
)

enum class Role {
    BUSINESS, INSPECTION
}