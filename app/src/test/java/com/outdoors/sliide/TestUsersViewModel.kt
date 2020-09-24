package com.outdoors.sliide

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.NetworkStatus
import com.outdoors.sliide.testutils.FakeRepository
import com.outdoors.sliide.testutils.LiveDataTestUtil
import com.outdoors.sliide.testutils.MainCoroutineRule
import com.outdoors.sliide.users.UsersListViewModel
import com.outdoors.sliide.utils.getFormattedCurrentTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestUsersViewModel
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeRepository: FakeRepository = FakeRepository()

    private lateinit var usersListViewModel: UsersListViewModel
    @Before
    fun setup()
    {

        usersListViewModel= UsersListViewModel(fakeRepository)
    }
    @Test
    fun testInitialStatus()
    {
        assert(""==usersListViewModel.errorMessage.value)
        assert(false==usersListViewModel.showError.value)
        assert(false==usersListViewModel.showProcessing.value)
        assert(null==usersListViewModel.currentUser.value)
    }

    @Test
    fun testGetUsers()
    {
       usersListViewModel.getUsersList()

        assert(NetworkStatus.SUCCESS== LiveDataTestUtil.getValue(usersListViewModel.users).status)
        assert(true== LiveDataTestUtil.getValue(usersListViewModel.users).data?.isNotEmpty())
        assert(9== LiveDataTestUtil.getValue(usersListViewModel.users).data?.size)
        assert(NetworkStatus.SUCCESS== LiveDataTestUtil.getValue(usersListViewModel.users).status)
        assert(null== LiveDataTestUtil.getValue(usersListViewModel.users).message)
        assert("1S67INYJNXI48DJ1YISB@yahoo.com"== LiveDataTestUtil.getValue(usersListViewModel.users).data?.get(0)?.email )
        assert(false==usersListViewModel.showProcessing.value)
    }

    @Test
    fun testSetCurrentUser()
    {
        val user = User(id=1999,name="Api Tester1",email="api.tester_new@example.com",gender = "Male",status = "Active",created_at = "2020-09-23T18:46:30.420+05:30")
        usersListViewModel.setCurrentUser(user)

        assert(user== LiveDataTestUtil.getValue(usersListViewModel.currentUser))
        assert(user.name== LiveDataTestUtil.getValue(usersListViewModel.currentUser)?.name)

        usersListViewModel.clearCurrentUser()

        assert(null == LiveDataTestUtil.getValue(usersListViewModel.currentUser))

    }

    @Test
    fun testDeleteUser() {
        val user = User(
            id = 1999,
            name = "Api Tester1",
            email = "api.tester_new@example.com",
            gender = null,
            status = null,
            created_at = getFormattedCurrentTime()
        )
        usersListViewModel.setCurrentUser(user)
        assert(user== LiveDataTestUtil.getValue(usersListViewModel.currentUser))

        usersListViewModel.deleteUser()
        assert(8== LiveDataTestUtil.getValue(usersListViewModel.users).data?.size)
    }

    @Test
    fun testAddUser() {
        usersListViewModel.addUser("newUser","test@test.com")

        assert(10== LiveDataTestUtil.getValue(usersListViewModel.users).data?.size)
        assert("newUser"== LiveDataTestUtil.getValue(usersListViewModel.users).data?.get(9)?.name)
        assert("test@test.com"== LiveDataTestUtil.getValue(usersListViewModel.users).data?.get(9)?.email)
    }
}