package com.app.retrofitexample.viewmodels

import androidx.lifecycle.*
import com.app.retrofitexample.DataStoreRepository
import com.app.retrofitexample.data.local.PostRequest
import com.app.retrofitexample.ui.main.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    var currentPage = 0
    var isLoading = true
    val allUser: LiveData<List<User>> = repository.getAll().asLiveData()

    fun getUsersFromServer() {
        viewModelScope.launch {
            repository.getUsers(currentPage)
            currentPage += 1
            isLoading = false
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun loadMoreUsers() {
        if (!isLoading) {
            isLoading = true
            getUsersFromServer()
        }
    }
}

data class UserListUiModel(
    val newUsers: List<User>,
    val hasMoreData: Boolean
)