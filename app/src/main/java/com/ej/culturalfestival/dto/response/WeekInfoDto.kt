package com.ej.culturalfestival.dto.response

import com.ej.culturalfestival.dto.StartEndDate
import java.time.LocalDate

data class WeekInfoDto(
    var weekRow : Int,
    val startEndDateList : MutableList<StartEndDate>,
)
