package com.example.interview

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.interview.api.APIInterface
import com.example.interview.database.AppDatabase
import com.example.interview.database.UserRepository
import com.example.interview.di.AppModule
import com.example.interview.models.User
import com.example.interview.utilites.BASE_URL
import com.example.interview.utilites.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    private val retrofitBuilder: APIInterface,
    private val app: Application
   ) : ViewModel(){

//    private var repository: UserRepository
    private val scope = CoroutineScope(Dispatchers.IO)


//    private val retrofitBuilder = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .build()
//        .create(APIInterface::class.java)
//    val x = AppModule.provideMyApi()

//    init {
////        val dao = AppDatabase.getDatabase(application).userDao()
////        repository = UserRepository(dao)
//       repository.
//    }

    //Check if device is connected to the Internet
    private fun isOnline(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i(TAG, "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i(TAG, "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }


    private fun addUsers(userList: List<User>) {
        scope.launch {
            repository.insert(userList)
        }
    }


    fun getUsers() = flow {
        // Emits users based on the Internet connection
        if (isOnline()) {
            val response = retrofitBuilder.getUsers()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    addUsers(it.data)
                    emit(it.data)
                }
            } else {
                repository.getAll().collect{
                    emit(it)
                }
            }
        } else {
            repository.getAll().collect{
                emit(it)
            }
        }
    }.flowOn(Dispatchers.IO)

}