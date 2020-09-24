package com.outdoors.sliide.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id:Int,
    val name:String,
    val email:String,
    val gender:String?=null,
    val status:String?=null,
    val created_at:String?=null
)
