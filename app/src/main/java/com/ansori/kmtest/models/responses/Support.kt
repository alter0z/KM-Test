package com.ansori.kmtest.models.responses

import com.google.gson.annotations.SerializedName

data class Support(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)