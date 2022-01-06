package com.app.retrofitexample.data.local

import androidx.room.*
import com.app.retrofitexample.ui.main.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Transaction
    @Query("Select * From user")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg user: User)

    @Query("Update user Set firstName= :name, lastName= :jobStates Where id=:id")
    fun update(name: String, jobStates: String, id: Int)

    @Delete
    fun delete(id: User)
}

@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}