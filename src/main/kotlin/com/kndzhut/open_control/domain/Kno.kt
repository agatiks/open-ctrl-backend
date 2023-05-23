package com.kndzhut.open_control.domain

class Kno(
    val id: Int,
    val name: String
)

class Measure(
    val id: Int,
    val name: String,
    val knoId: Int
)

class MeasureDto(
    val id: Int,
    val name: String,
)

