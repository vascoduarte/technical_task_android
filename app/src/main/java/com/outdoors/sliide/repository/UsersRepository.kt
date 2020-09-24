package com.outdoors.sliide.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.GoRestAPI
import com.outdoors.sliide.network.NetworkResource
import com.outdoors.sliide.network.asDomainModel
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(private val network:GoRestAPI,

):BaseUsersRepository {


    override suspend fun getUsers():NetworkResource<List<User>>
    {
        return try {
            val tempList = network.getUsersList(null)
            val lastPage = tempList.meta.pagination.pages
            val networkList = network.getUsersList(lastPage)
            if (networkList.code == 200) {
                NetworkResource.success(data = networkList.data.asDomainModel())
            }
            else
            {
                NetworkResource.error(data = null, message = "Error Occurred!")
            }
        } catch (e:Exception) {
            NetworkResource.error(data = null, message = e.message ?: "Error Occurred!")
        }
    }

    override suspend fun deleteUser(id:Int):Boolean
    {
        return try {
            val networkReply = network.deleteUser(id)
            networkReply.code==204

        } catch (e:Exception) {
            false
        }
    }

    override suspend fun addUser(user: User): NetworkResource<User> {
        val action = network.addUser(user.name,user.email,user.status!!,user.gender!!)
        return if (action.code == 200 || action.code == 201) {
            NetworkResource.success(action.data.asDomainModel())
        } else NetworkResource.error(data = null, message = "Error Occurred!")

    }

}