package com.ej.culturalfestival.api

import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.dto.response.ResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FestivalApi {

    @GET("/api/festival/search/date")
    fun getFestival(@Query("startDate") startDate : String, @Query("endDate") endDate : String) : Call<ResponseDto<MutableList<FestivalDto>>>

    @GET("/api/festival/search/{id}")
    fun getFestival(@Path("id")id : Long) : Call<ResponseDto<FestivalDto>>

    @GET("/api/festival/search/title/{str}")
    fun getFestivalByTitle(@Path("str") str: String): Call<ResponseDto<MutableList<FestivalDto>>>
}