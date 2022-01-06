package com.app.retrofitexample.data.local

import com.google.gson.annotations.SerializedName


data class PostResponse(
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("job")
    var job: String,
    @SerializedName("name")
    var name: String
)

data class PostRequest(
    @SerializedName("job")
    var job: String,
    @SerializedName("name")
    var name: String
)
