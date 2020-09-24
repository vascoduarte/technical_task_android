package com.outdoors.sliide.network

import com.outdoors.sliide.domain.User
import retrofit2.http.*

interface GoRestAPI {
    @GET("users")
    suspend fun getUsersList( @Query("page") page:Int? ):NetworkUsersContainer

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id:Int ):NetworkDeleteContainer

    @FormUrlEncoded
    @POST("users")
    suspend fun addUser(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("status") status:String,
        @Field("gender") gender:String
    ):NetworkAddContainer
}