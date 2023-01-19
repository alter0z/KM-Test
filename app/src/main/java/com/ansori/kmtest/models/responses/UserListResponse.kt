package com.ansori.kmtest.models.responses

import com.google.gson.annotations.SerializedName

data class UserListResponse(

	@field:SerializedName("per_page")
	val perPage: Int? = 0,

	@field:SerializedName("total")
	val total: Int? = 0,

	@field:SerializedName("data")
	val data: MutableList<DataItem>?,

	@field:SerializedName("page")
	val page: Int? = 0,

	@field:SerializedName("total_pages")
	val totalPages: Int? = 0,

	@field:SerializedName("support")
	val support: Support? = null
)