package com.ej.culturalfestival.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ej.culturalfestival.api.FestivalFetchr
import com.ej.culturalfestival.dto.response.FestivalDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FestivalViewModel :ViewModel(){

    private val festivalFetchr : FestivalFetchr by lazy { FestivalFetchr() }

    private val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getFestival(firstLocalDate : LocalDate, lastLocalDate : LocalDate) : LiveData<MutableList<FestivalDto>>{
        val firstDate = formatter.format(firstLocalDate)
        val lastDate = formatter.format(lastLocalDate)
        val result = festivalFetchr.getFestival(firstDate,lastDate)
        return result
    }

}