package com.example.interview.recyclerview

import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.interview.R
import com.example.interview.databinding.UserListItemBinding
import com.example.interview.models.User

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding = UserListItemBinding.bind(view)

    fun bindData(user: User){
        val image = user.avatar.toUri().buildUpon().scheme("https").build()
        binding.avatar.load(image) {
            this.placeholder(R.color.white)
            this.error(R.drawable.iconerror)
        }
        binding.firstname.text = user.first_name
        binding.lastname.text = user.last_name
        binding.email.text = user.email
    }

}