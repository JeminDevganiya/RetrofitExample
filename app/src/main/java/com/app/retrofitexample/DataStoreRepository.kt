package com.app.retrofitexample

import com.app.retrofitexample.api.UsersService
import com.app.retrofitexample.data.local.PostRequest
import com.app.retrofitexample.data.local.UserDao
import com.app.retrofitexample.ui.main.ListUserResponse
import com.app.retrofitexample.ui.main.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DataStoreRepository {
    suspend fun getUsers(page: Int): ListUserResponse
    suspend fun postUser(name: String, jobStates: String): Boolean
    suspend fun updateUser(name: String, jobStates: String, id: Int): Boolean
    suspend fun deleteUser(user: User): Boolean
    fun getAll(): Flow<List<User>>
}
class DataStoreRepositoryImpl @Inject constructor(
    private val usersService: UsersService,
    private val userDao: UserDao
) : DataStoreRepository {
    override suspend fun getUsers(page: Int): ListUserResponse {
        val response = usersService.getUser(page)
        userDao.insertUsers(*response.users.toTypedArray())
        return response
    }
    override suspend fun postUser(name: String, jobStates: String): Boolean {
        return try {
            usersService.postUser(PostRequest(jobStates, name))
            true
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun updateUser(name: String, jobStates: String, id: Int): Boolean {
        return try {
            usersService.updateUser(id, PostRequest(jobStates, name))
            userDao.update(name, jobStates, id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
    override suspend fun deleteUser(user: User): Boolean {
        return try {
            usersService.deleteUser(user)
            userDao.delete(user)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override fun getAll(): Flow<List<User>> = userDao.getAllUsers()
}