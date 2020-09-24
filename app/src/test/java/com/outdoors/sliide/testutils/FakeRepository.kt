package com.outdoors.sliide.testutils

import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.NetworkResource
import com.outdoors.sliide.network.NetworkUsersContainer
import com.outdoors.sliide.network.asDomainModel
import com.outdoors.sliide.repository.BaseUsersRepository
import kotlinx.serialization.json.Json

class FakeRepository:BaseUsersRepository {
    override suspend fun getUsers(): NetworkResource<List<User>> {

        val users: NetworkUsersContainer =
            Json.decodeFromString(NetworkUsersContainer.serializer(), getUsersRawData())

        return  NetworkResource.success(data =users.data.asDomainModel())
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return true
    }

    override suspend fun addUser(user: User): NetworkResource<User> {

        return NetworkResource.success(user)
    }

    private fun getUsersRawData():String
    {
            return """{"code":200,"meta":{"pagination":{"total":1749,"pages":88,"page":88,"limit":20}},"data":[
|{"id":1985,"name":"Tony","email":"1S67INYJNXI48DJ1YISB@yahoo.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:52.064+05:30","updated_at":"2020-09-23T18:45:52.064+05:30"},
|{"id":1986,"name":"Tony","email":"ONNBTDG9AY6CRJIVDUBC@yahoo.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:52.132+05:30","updated_at":"2020-09-23T18:45:52.132+05:30"},
|{"id":1987,"name":"Tony","email":"ZGZ7M36LGI2B4653YRJ9@yahoo.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:52.368+05:30","updated_at":"2020-09-23T18:45:52.368+05:30"},
|{"id":1988,"name":"Tony","email":"JM6HVC62RQ16ZFGU5QSD@other.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:52.580+05:30","updated_at":"2020-09-23T18:45:52.580+05:30"},
|{"id":1995,"name":"Tony","email":"55F2UHFWM68ZDCOIRWEL@yahoo.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:54.680+05:30","updated_at":"2020-09-23T18:45:54.680+05:30"},
|{"id":1996,"name":"Tony","email":"96D488BMYN1XK07P9MZF@hotmail.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:54.749+05:30","updated_at":"2020-09-23T18:45:54.749+05:30"},
|{"id":1997,"name":"Tony","email":"313MUN7M14Q448F1T8LU@hotmail.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:55.829+05:30","updated_at":"2020-09-23T18:45:55.829+05:30"},
|{"id":1998,"name":"Tony","email":"TKT3VJFTJ8OFR6BSGOHE@hotmail.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:45:56.059+05:30","updated_at":"2020-09-23T18:45:56.059+05:30"},
|{"id":1999,"name":"Api Tester1","email":"api.tester_new@example.com","gender":"Male","status":"Active","created_at":"2020-09-23T18:46:30.420+05:30","updated_at":"2020-09-23T18:46:30.420+05:30"}]}""".trimMargin()
    }


}