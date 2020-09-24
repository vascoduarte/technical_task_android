package com.outdoors.sliide.network

import com.outdoors.sliide.domain.User
import com.outdoors.sliide.utils.getFormattedCurrentTime
import kotlinx.serialization.Serializable



@Serializable
data class NetworkUsersContainer(val code:Int, val meta:NetworkMetaData ,val data:List<NetworkUser>)

@Serializable
data class NetworkMetaData(val pagination:NetworkPagination)

@Serializable
data class NetworkPagination(
    val total:Int,
    val pages:Int,
    val page:Int,
    val limit:Int
)

@Serializable
data class NetworkUser(
    val id:Int,
    val name:String,
    val email:String,
    val gender:String,
    val status:String,
    val created_at:String,
    val updated_at:String,
)

fun List<NetworkUser>.asDomainModel():List<User>
{
    return map{
        User(
            id=it.id,
            name = it.name,
            email = it.email,
            created_at = getFormattedCurrentTime()
        )
    }
}
fun NetworkUser.asDomainModel():User
{
    return User(
            id=this.id,
            name = this.name,
            email = this.email,
            created_at = getFormattedCurrentTime()
        )
}

@Serializable
data class NetworkDeleteContainer(val code:Int)

@Serializable
data class NetworkAddContainer(val code:Int,val data:NetworkUser)





