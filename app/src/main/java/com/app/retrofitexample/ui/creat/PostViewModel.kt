package com.app.retrofitexample.ui.creat

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.retrofitexample.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val apiRes: LiveData<Boolean> = _isLoading

    private val _isSuccess: MutableLiveData<Unit> = MutableLiveData()
    val success: LiveData<Unit> = _isSuccess

    private val _isError: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _isError

    fun postUser(name: String, jobStates: String) {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.message
        }) {
            _isLoading.value = true
            val response1 = repository.postUser(name, jobStates)
            if (response1) {
                _isSuccess.value = Unit
            }
            _isLoading.value = false
        }
    }
    fun updateUser(name: String, jobStates: String, id: Int) {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.message
        }) {
            _isLoading.value = true
            val response2 = repository.updateUser(name, jobStates, id)
            if (response2) {
                _isSuccess.value = Unit
            }
            _isLoading.value = false
        }
    }
}