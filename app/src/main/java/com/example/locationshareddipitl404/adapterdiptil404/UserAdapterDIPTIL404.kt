package com.example.locationshareddipitl404.adapterdiptil404

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationshareddipitl404.databinding.ItemUserdiptil4Binding

import com.example.locationshareddipitl404.modelDiptil404.UserDiptiL404


class UserAdapterDIPTIL404(private var userList: List<UserDiptiL404>) : RecyclerView.Adapter<UserAdapterDIPTIL404.UserViewHolder>() {

    class UserViewHolder(private val binding: ItemUserdiptil4Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDiptiL404) {

            binding.apply {
                displayNameTxt.text = user.displayName
                emailTxt.text = user.email
                locationTxt.text = user.location

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserdiptil4Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)

    }
    fun updateData(newList: List<UserDiptiL404>) {

        userList = newList
        notifyDataSetChanged()
    }
}