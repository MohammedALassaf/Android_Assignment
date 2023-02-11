package com.example.interview.database

import android.util.Log
import com.example.interview.models.User
import com.example.interview.utilites.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.math.log

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun insert(users: List<User>){
        userDao.insertUsers(users)
    }
    suspend fun update(user: User){
        userDao.updateUser(user)
    }

    suspend fun delete(user: User){
        userDao.deleteUser(user)
    }
    suspend fun getAll() = flow<List<User>> {
//        Log.d(TAG, "getAll: ")
        emit(userDao.getAll())
    }.flowOn(Dispatchers.IO)

}