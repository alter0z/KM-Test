package com.ansori.kmtest.models.service

import com.ansori.kmtest.models.responses.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // request get user list
    @GET("users")
    suspend fun getUserList(@Query("page") page: Int, @Query("per_page") perPage: Int): Response<UserListResponse?>
}