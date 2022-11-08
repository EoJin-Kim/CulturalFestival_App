package com.ej.culturalfestival.di.module

import com.ej.culturalfestival.api.FestivalApi
import com.ej.culturalfestival.util.ServerInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Singleton
//    @Provides
//    fun provideAboutMeFetchr() : AboutMeFetchr{
//        return AboutMeFetchr()
//    }

    @Singleton
    @Provides
    fun provideFestivalApi() : FestivalApi{
        val retrofit = Retrofit.Builder()
            .baseUrl(ServerInfo.SERVER_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val festaivalApi = retrofit.create(FestivalApi::class.java)
        return festaivalApi
    }
}