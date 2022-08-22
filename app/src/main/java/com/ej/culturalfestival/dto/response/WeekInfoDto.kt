package com.ej.culturalfestival.dto.response

import java.time.LocalDate

data class WeekInfoDto(
    val weekRow : Int,
    val startDate : LocalDate,
    val endDate : LocalDate
)
