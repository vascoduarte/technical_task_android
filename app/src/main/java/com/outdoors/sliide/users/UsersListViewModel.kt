package com.outdoors.sliide.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.outdoors.sliide.domain.User
import com.outdoors.sliide.network.NetworkResource
import com.outdoors.sliide.network.NetworkStatus
import com.outdoors.sliide.repository.BaseUsersRepository
import com.outdoors.sliide.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UsersListViewModel @ViewModelInject constructor(private val usersRepository: BaseUsersRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _users:MutableLiveData<NetworkResource<List<User>>> = MutableLiveData()
    val users: LiveData<NetworkResource<List<User>>>
        get() = _users

    private val _currentUser:MutableLiveData<User?> = MutableLiveData()
    val currentUser: LiveData<User?>
        get() = _currentUser

    private val _showProcessing:MutableLiveData<Boolean> = MutableLiveData()
    val showProcessing: LiveData<Boolean>
        get() = _showProcessing

    private val _showError:MutableLiveData<Boolean> = MutableLiveData()
    val showError: LiveData<Boolean>
        get() = _showError

    private val _errorMessage:MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    var showingDelete:Boolean = false
    var showingAdd:Boolean = false


    init{
        _currentUser.value=null
        _showProcessing.value=true
        _errorMessage.value=""
        _showError.value=false

        getUsersList()

    }
     fun getUsersList()
     {
         viewModelScope.launch {
             val list =usersRepository.getUsers()
             if(list.status==NetworkStatus.SUCCESS) {
                 _users.value = list
             }
             else
             {
                 setError(list.message!!)
             }
             _showProcessing.value=false
         }
     }

    fun setCurrentUser(user:User)
    {
        _currentUser.value = user
        showingDelete=true
    }

    fun clearCurrentUser()
    {
        _currentUser.value = null
        showingDelete=false
    }
    fun proceedWithDelete()
    {
        _showProcessing.value=true
        viewModelScope.launch {
            val deleteSuccess =  usersRepository.deleteUser(_currentUser.value?.id!!)
            if(deleteSuccess) deleteUser()
            else
            {
                setError("Failed to delete user!")
                clearCurrentUser()
            }
        }
    }
    fun deleteUser()
    {
        val prevList:MutableList<User>? =  _users.value?.data?.toMutableList()
        prevList?.let {
            prevList.remove(_currentUser.value)
            val list = prevList.toList()
            _users.value=NetworkResource.success(data = list )
            _showProcessing.value=false
        }
       clearCurrentUser()
    }

    fun addUser(name:String,email:String)
    {
        _showProcessing.value=true
        val newUser = getNewUser(name,email)

        viewModelScope.launch {
            val confirmedUser = usersRepository.addUser(newUser)
            if(confirmedUser.status==NetworkStatus.SUCCESS)
            {
                val prevList:MutableList<User>? =  _users.value?.data?.toMutableList()
                prevList?.let {
                    prevList.add(confirmedUser.data!!)
                    val list = prevList.toList()
                    _users.value=NetworkResource.success(data = list )
                }
            }
            else
            {
                setError(confirmedUser.message!!)
            }
            _showProcessing.value=false
        }
    }
    private fun getNewUser(newName:String,newEmail:String):User
    {
        return User(
            id=-1,
            name = newName,
            email = newEmail,
            status = "Active",
            gender = "Male"
        )
    }
    private fun setError(message:String)
    {
        _errorMessage.value=message
        _showError.value=true
    }
    fun errorShown()
    {
        _errorMessage.value=""
        _showError.value=false
    }
}