package com.app.retrofitexample.di

import android.content.Context
import androidx.room.Room
import com.app.retrofitexample.data.local.AppDataBase
import com.app.retrofitexample.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    @Provides
    fun providesUserDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.userDao()
    }

    @Provides
    @Singleton
    fun providesAppDataBase(@ApplicationContext appContext: Context):
            AppDataBase {
        return Room.databaseBuilder(
            appContext, AppDataBase::class.java, "User"
        ).allowMainThreadQueries().build()
    }
}