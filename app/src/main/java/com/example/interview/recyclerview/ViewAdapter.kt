package com.example.interview.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interview.R
import com.example.interview.models.User


class ViewAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        if(holder is ViewHolder) holder.bindData(user)
    }
}