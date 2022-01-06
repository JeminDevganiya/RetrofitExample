package com.app.retrofitexample.ui.main

import android.content.Context
import android.os.IInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.retrofitexample.databinding.ListTemProgressBinding
import com.app.retrofitexample.databinding.RowTextBinding
import com.bumptech.glide.Glide

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private val users: MutableList<User> = mutableListOf()

    fun addUser(newUsers: List<User>, hasMoreData: Boolean) {
        users.clear()
        users.addAll(newUsers)
        if (hasMoreData) {
            users.add(User(0, "", "", "", ""))
        }
        notifyDataSetChanged()
    }

    sealed class MyViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        data class UserViewHolder(val binding: RowTextBinding) : MyViewHolder(binding.root)
        data class ProgressViewHolder(val binding: ListTemProgressBinding) :
            MyViewHolder(binding.root)
    }

    var click: (User) -> Unit = {}

    var deleteClick: (User) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return if (viewType == TYPE_USER) {
            MyViewHolder.UserViewHolder(
                RowTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            MyViewHolder.ProgressViewHolder(
                ListTemProgressBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder.ProgressViewHolder -> {
                // do nothing
            }
            is MyViewHolder.UserViewHolder -> {
                Glide.with(holder.binding.userPhoto.context)
                    .load(users[position].avatar)
                    .centerCrop()
                    .into(holder.binding.userPhoto)
                holder.binding.firtName.text = users[position].firstName
                holder.binding.lastName.text = users[position].lastName
                holder.binding.userEmail.text = users[position].email
                holder.binding.cell.setOnClickListener {
                    click(users[position])
                }
                holder.binding.deleteButton.setOnClickListener{
                    deleteClick(users[position])
                }
            }
        }
    }

    override fun getItemCount() = users.size

    override fun getItemViewType(position: Int): Int =
        if (users[position].id == 0) TYPE_PROGRESS else TYPE_USER

    companion object {
        const val TYPE_PROGRESS = 0
        const val TYPE_USER = 1
    }
}