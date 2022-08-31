package com.ej.culturalfestival.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ej.culturalfestival.api.FestivalApi
import com.ej.culturalfestival.api.FestivalFetchr
import com.ej.culturalfestival.dto.response.FestivalDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FestivalViewModel :ViewModel(){

    private val festivalFetchr : FestivalFetchr by lazy { FestivalFetchr() }

    private val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var _festivalSearchResult = MutableLiveData<MutableList<FestivalDto>>()
    val festivalSearchResult : LiveData<MutableList<FestivalDto>>
        get() = _festivalSearchResult

    var openUrl : String = ""

    private val _dayFragmentDate = MutableLiveData<LocalDate>()
    val dayFragmentDate : LiveData<LocalDate>
        get() = _dayFragmentDate

    fun setDayFragmentDate(date: LocalDate){
        _dayFragmentDate.postValue(date)
    }


    fun getFestival(firstLocalDate : LocalDate, lastLocalDate : LocalDate) : LiveData<MutableList<FestivalDto>>{
        val firstDate = formatter.format(firstLocalDate)
        val lastDate = formatter.format(lastLocalDate)
        val result = festivalFetchr.getFestival(firstDate,lastDate)
        return result
    }

    fun getFestival(id : Long) : LiveData<FestivalDto>{

        val result = festivalFetchr.getFestival(id)
        return result
    }

    fun getFestivalByTitle(str : String):LiveData<MutableList<FestivalDto>>{
        _festivalSearchResult = festivalFetchr.getFestivalByTitle(str)
        return festivalSearchResult
    }

}