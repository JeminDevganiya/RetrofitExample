package com.app.retrofitexample.ui.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListUserResponse(
    @SerializedName("data")
    var users: List<User>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var avatar: String,
    var email: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String
) : Serializable
