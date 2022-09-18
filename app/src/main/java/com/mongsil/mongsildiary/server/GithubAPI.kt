package com.mongsil.mongsildiary.server

import com.mongsil.mongsildiary.domain.Saying
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {
    @GET("Desktop/sayingList.json")
    fun getSayingList(): Call<Saying>

//    @GET("Desktop/sayingList.json")
//    fun getSayingList(@Query("sayingList") key:String): Call<Saying>
}