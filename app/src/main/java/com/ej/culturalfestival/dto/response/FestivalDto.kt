package com.ej.culturalfestival.dto.response

data class FestivalDto (
    val id : Long,
    // 축제 명
    val fstvlNm : String,
    // 개최장소
    val opar : String,
    // 시작일자
    val fstvlStartDate : String,
    // 종료 일자
    val fstvlEndDate : String,
    // 축제 내용
    val fstvlCo : String,
    // 주관기관
    val mnnst : String,
    // 주최기관
    val auspcInstt : String,
    // 후원기관
    val suprtInstt : String,
    // 전화번호
    val phoneNumber : String,
    // 홈페이지 주소
    val homepageUrl : String,
    // 관련 정보
    val relateInfo : String,
    // 소재지 도로명 주소
    val rdnmadr : String,
    // 소재지 지번 주소
    val lnmadr : String,
    // 위도
    val latitude : String,
    // 경도
    val longitude : String,
    // 데이터 기준 일자
    val referenceDate : String,
    // 제공 기관 코드
    val insttCode : String,
)