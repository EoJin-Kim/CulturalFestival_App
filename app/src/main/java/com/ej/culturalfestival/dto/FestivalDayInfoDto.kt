package com.ej.culturalfestival.dto

import java.time.LocalDate
import kotlin.Int

data class FestivalDayInfoDto (
    val date : LocalDate,
    val count : Int,
)