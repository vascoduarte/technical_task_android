package com.outdoors.sliide

import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.NetworkStatus
import com.outdoors.sliide.testutils.FakeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TestRepository ()
{
    private val fakeRepository: FakeRepository = FakeRepository()
    @Before
    fun setup()
    {

    }
    @Test
    fun testListUsers()= runBlocking {

        val userList=  fakeRepository.getUsers()

        assert(true==userList.data?.isNotEmpty())
        assert(9==userList.data?.size)
        assert(NetworkStatus.SUCCESS==userList.status)
        assert(null==userList.message)
        assert("1S67INYJNXI48DJ1YISB@yahoo.com"== userList.data?.get(0)?.email )
    }

    @Test
    fun testAddUsers()= runBlocking {

        val newUser = User(id=10,name="New user",email="test@test.com",gender = "Male",status = "Active")
        val user=  fakeRepository.addUser(newUser)

        assert(null!=user.data)
        assert("test@test.com"== user.data?.email)
    }

}