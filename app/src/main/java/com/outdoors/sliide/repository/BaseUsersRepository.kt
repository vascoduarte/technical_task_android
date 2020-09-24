package com.outdoors.sliide.repository

import androidx.lifecycle.MutableLiveData
import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.NetworkResource

interface BaseUsersRepository {



    suspend fun getUsers():NetworkResource<List<User>>

    suspend fun deleteUser(id:Int):Boolean

    suspend fun addUser(user:User):NetworkResource<User>
}