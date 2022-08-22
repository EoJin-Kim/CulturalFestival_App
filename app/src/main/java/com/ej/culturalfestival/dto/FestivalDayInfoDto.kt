package com.ej.culturalfestival.dto

import java.time.LocalDate

data class FestivalDayInfoDto (
    val date : LocalDate,
    val count : Int,
)