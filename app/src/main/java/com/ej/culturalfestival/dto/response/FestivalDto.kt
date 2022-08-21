package com.ej.culturalfestival.dto.response

data class FestivalDto (
    val id : Long,
    val fstvlNm : String,
    val opar : String,
    val fstvlStartDate : String,
    val fstvlEndDate : String,
    val fstvlCo : String,
    val mnnst : String,
    val auspcInstt : String,
    val suprtInstt : String,
    val phoneNumber : String,
    val homepageUrl : String,
    val relateInfo : String,
    val rdnmadr : String,
    val lnmadr : String,
    val latitude : String,
    val longitude : String,
    val referenceDate : String,
    val insttCode : String,
)