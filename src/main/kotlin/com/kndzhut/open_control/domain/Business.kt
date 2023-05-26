package com.kndzhut.open_control.domain

import java.util.*

class Business(
    val id: UUID,
    val userId: String,
    val type: String,
    val kind: String,
    val objectClass: String,
    val activityClass: String,
    val address: String
)