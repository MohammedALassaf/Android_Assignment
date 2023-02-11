package com.example.interview

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.interview.database.AppDatabase
import com.example.interview.database.UserRepository
import com.example.interview.databinding.ActivityMainBinding
import com.example.interview.models.User
import com.example.interview.recyclerview.ViewAdapter
import com.example.interview.utilites.TAG
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ViewAdapter
    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        scope.launch {
            viewModel.getUsers().collect {
                listAdapter(it)
            }
        }


    }

    private fun listAdapter(users: List<User>) {
        adapter = ViewAdapter(users)
        binding.recyclerView.adapter = adapter

    }


}