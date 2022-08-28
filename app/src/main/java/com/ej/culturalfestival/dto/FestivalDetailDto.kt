package com.ej.culturalfestival.dto

import com.ej.culturalfestival.dto.response.FestivalDto
import java.lang.reflect.Constructor

data class FestivalDetailDto(
    val id : Long,
    val title : String,
    val content : String,
    val phone : String,
    // 주최기관
    val auspcInstt : String,
    // 후원기관
    val suprtInstt : String,
    // 소재지 도로명 주소
    val rdnmadr : String,
    // 소재지 지번 주소
    val lnmadr : String,
    val date : String,
    val homepage : String,){
    constructor(festivalDto: FestivalDto): this(
        festivalDto.id,
        festivalDto.fstvlNm,
        festivalDto.fstvlCo,
        festivalDto.phoneNumber,
        festivalDto.auspcInstt,
        festivalDto.suprtInstt,
        festivalDto.rdnmadr,
        festivalDto.lnmadr,
        "${festivalDto.fstvlStartDate} ~ ${festivalDto.fstvlEndDate}",
        festivalDto.homepageUrl
    )
}
