package com.ej.culturalfestival.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarUtil {

    companion object{
        lateinit var selectedDate : LocalDate
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    }
}