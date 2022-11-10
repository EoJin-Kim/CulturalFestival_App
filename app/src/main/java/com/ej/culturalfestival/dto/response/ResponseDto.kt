package com.ej.culturalfestival.dto.response


data class ResponseDto<T>(
    val status : String,
    val response : T
)
