package com.ej.culturalfestival.dto.response

import java.time.LocalDate

data class WeekInfoDto(
    var weekRow : Int,
    var startDate : LocalDate,
    var endDate : LocalDate
)
