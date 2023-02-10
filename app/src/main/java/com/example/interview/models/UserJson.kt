package com.example.interview.models

import com.google.gson.annotations.SerializedName

data class UserJson(
    @SerializedName("data")
    val `data`: List<User>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)