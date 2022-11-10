package com.ej.culturalfestival.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ej.culturalfestival.api.FestivalApi
import com.ej.culturalfestival.api.FestivalFetchr
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.dto.WeekInfoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val festivalApi: FestivalApi
) :ViewModel(){

    private val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    private var _festivalOne = MutableLiveData<FestivalDto>()
    val festivalOne : LiveData<FestivalDto>
        get() = _festivalOne

    private var _festivalSearchResult = MutableLiveData<MutableList<FestivalDto>>()
    val festivalSearchResult : LiveData<MutableList<FestivalDto>>
        get() = _festivalSearchResult


    private val _dayFragmentDate = MutableLiveData<LocalDate>()
    val dayFragmentDate : LiveData<LocalDate>
        get() = _dayFragmentDate


    private val _weekFragmentDate = MutableLiveData<WeekInfoDto>()
    val weekFragmentDate : LiveData<WeekInfoDto>
        get() = _weekFragmentDate

    private val _monthFragmentDate = MutableLiveData<LocalDate>()
    val monthFragmentDate : LiveData<LocalDate>
        get() = _monthFragmentDate

    var openUrl : String = ""

    fun setDayFragmentDate(date: LocalDate){
        _dayFragmentDate.value = date
    }

    fun setWeekFragmentDate(weekInfoDto: WeekInfoDto){
        _weekFragmentDate.value = weekInfoDto
    }

    fun setMonthFragmentDate(date: LocalDate){

        _monthFragmentDate.value = date
    }


    fun getFestival(firstLocalDate : LocalDate, lastLocalDate : LocalDate) {
        val firstDate = formatter.format(firstLocalDate)
        val lastDate = formatter.format(lastLocalDate)
        viewModelScope.launch {
            _festivalSearchResult.value = festivalApi.getFestival(firstDate,lastDate).response!!
        }
    }

    fun getFestival(id : Long){
        viewModelScope.launch {
            _festivalOne.value = festivalApi.getFestival(id).response!!
        }
    }

    fun getFestivalByTitle(str : String){
        viewModelScope.launch {
            _festivalSearchResult.value = festivalApi.getFestivalByTitle(str).response!!
        }
    }

}