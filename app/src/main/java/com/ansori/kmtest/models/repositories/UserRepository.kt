package com.ansori.kmtest.models.repositories

import com.ansori.kmtest.api.ApiConfig

class UserRepository {
    private val client = ApiConfig.getApiService()

    suspend fun getUserList(page: Int, perPage: Int) = client.getUserList(page, perPage)
}