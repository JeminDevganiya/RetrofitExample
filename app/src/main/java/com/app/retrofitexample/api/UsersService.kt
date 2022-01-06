package com.app.retrofitexample.api

import com.app.retrofitexample.data.local.PostRequest
import com.app.retrofitexample.data.local.PostResponse
import com.app.retrofitexample.ui.main.ListUserResponse
import com.app.retrofitexample.ui.main.User
import retrofit2.http.*

interface UsersService {
    @GET("users")
    suspend fun getUser(@Query("page") page: Int): ListUserResponse

    @POST("users")
    suspend fun postUser(@Body users: PostRequest): PostResponse

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body users: PostRequest): PostResponse

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: User): PostResponse
}