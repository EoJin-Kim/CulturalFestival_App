package com.ej.culturalfestival.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.dto.response.ResponseDto
import com.ej.culturalfestival.util.ServerInfo.Companion.SERVER_URL
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class FestivalFetchr {

    private val festivalApi: FestivalApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        festivalApi = retrofit.create(FestivalApi::class.java)
    }

     fun getFestival(startDate : String, endDate:String) : LiveData<MutableList<FestivalDto>> {
         var result : MutableLiveData<MutableList<FestivalDto>> = MutableLiveData()
         val festivalRequest = festivalApi.getFestival(startDate,endDate)

         festivalRequest.enqueue(object : Callback<ResponseDto<MutableList<FestivalDto>>> {
             override fun onResponse(
                 call: Call<ResponseDto<MutableList<FestivalDto>>>,
                 response: Response<ResponseDto<MutableList<FestivalDto>>>
             ) {
                 val aboutMeResponse : ResponseDto<MutableList<FestivalDto>>? = response.body()
                 result.value = aboutMeResponse!!.response
             }

             override fun onFailure(
                 call: Call<ResponseDto<MutableList<FestivalDto>>>,
                 t: Throwable
             ) {
                 Log.d("http","request error")
             }
         })
         return result
    }

    fun getFestival(id : Long) : LiveData<FestivalDto> {
        var result : MutableLiveData<FestivalDto> = MutableLiveData()
        val festivalRequest = festivalApi.getFestival(id)

        festivalRequest.enqueue(object : Callback<ResponseDto<FestivalDto>> {
            override fun onResponse(
                call: Call<ResponseDto<FestivalDto>>,
                response: Response<ResponseDto<FestivalDto>>
            ) {
                val aboutMeResponse : ResponseDto<FestivalDto>? = response.body()
                result.value = aboutMeResponse!!.response
            }

            override fun onFailure(
                call: Call<ResponseDto<FestivalDto>>,
                t: Throwable
            ) {
                Log.d("http","request error")
            }
        })
        return result
    }
}