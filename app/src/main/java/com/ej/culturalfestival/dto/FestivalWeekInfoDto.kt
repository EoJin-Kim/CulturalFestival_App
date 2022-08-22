package com.ej.culturalfestival.dto

import java.time.LocalDate

data class FestivalWeekInfoDto (
    val date: LocalDate,
    val festivalSummaryDtoList : MutableList<FestivalSummaryDto>
)
