package com.example.interview.api

import com.example.interview.models.UserJson
import retrofit2.Response
import retrofit2.http.GET


interface APIInterface {

    @GET("users")
    suspend fun getUsers(): Response<UserJson>

}