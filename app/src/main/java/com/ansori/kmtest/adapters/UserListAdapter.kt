package com.ansori.kmtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ansori.kmtest.BuildConfig
import com.ansori.kmtest.databinding.UserListBinding
import com.ansori.kmtest.listeners.OnUserClickListener
import com.ansori.kmtest.models.responses.DataItem
import com.bumptech.glide.Glide

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private lateinit var onUserClickListener: OnUserClickListener

    fun onUserClickListener(onUserClickListener: OnUserClickListener) {
        this.onUserClickListener = onUserClickListener
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding: UserListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(itemView).load(avatar).circleCrop().into(binding.avatar)
                val username = "$firstName $lastName"
                binding.name.text = username
                binding.email.text = email

                itemView.setOnClickListener { onUserClickListener.onUserClick(username) }

//                if (position == 0){
//                    val params = itemView.layoutParams as RecyclerView.LayoutParams
//                    params.topMargin = 220
//                    itemView.layoutParams = params
//                } else if (position == differ.currentList.lastIndex) {
//                    val params = itemView.layoutParams as RecyclerView.LayoutParams
//                    params.bottomMargin = 80
//                    itemView.layoutParams = params
//                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size
}