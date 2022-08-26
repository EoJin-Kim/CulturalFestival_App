package com.ej.culturalfestival.dto

import com.ej.culturalfestival.dto.response.FestivalDto
import java.lang.reflect.Constructor

data class FestivalDetailDto(
    val id : Long,
    val title : String,
    val content : String,
    val phone : String,
    val address : String,
    val date : String,
    val homepage : String,){
    constructor(festivalDto: FestivalDto): this(
        festivalDto.id,
        festivalDto.fstvlNm,
        festivalDto.fstvlCo,
        festivalDto.phoneNumber,
        festivalDto.rdnmadr,
        "${festivalDto.fstvlStartDate} ~ ${festivalDto.fstvlEndDate}",
        festivalDto.homepageUrl
    )
}
