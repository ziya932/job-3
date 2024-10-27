package com.example.locationshareddipitl404.adapter_dipti_amad_ict_l4_04

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationshareddipitl404.databinding.ItemUserDiptiAmadIctL404Binding

import com.example.locationshareddipitl404.model_dipti_amad_ict_l4_04.UserDipti_amad_ict_L4_04


class UserAdapterDIPTI_amad_ict_L4_04(private var userList: List<UserDipti_amad_ict_L4_04>) : RecyclerView.Adapter<UserAdapterDIPTI_amad_ict_L4_04.UserViewHolder>() {

    class UserViewHolder(private val binding: ItemUserDiptiAmadIctL404Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDipti_amad_ict_L4_04) {

            binding.apply {
                displayNameTxt.text = user.displayName
                emailTxt.text = user.email
                locationTxt.text = user.location

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserDiptiAmadIctL404Binding.inflate(
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
    fun updateData(newList: List<UserDipti_amad_ict_L4_04>) {

        userList = newList
        notifyDataSetChanged()
    }
}