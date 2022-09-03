package com.ej.culturalfestival.util

import com.ej.culturalfestival.dto.LocalDateDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarUtil {

    companion object{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun yearMonthFromDate(date : LocalDate):String{
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
            return date.format(formatter)
        }
        fun dateListTodateDtoList(
            dateList: MutableList<LocalDate?>,
        ) : MutableList<LocalDateDto>{
            val dateDtoList = mutableListOf<LocalDateDto>()

            for (localDate in dateList) {
                dateDtoList.add(LocalDateDto(localDate))
            }
            return dateDtoList
        }

    }

}