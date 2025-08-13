package com.maad.jobmarket.home_notification_Nahid.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maad.jobmarket.databinding.ItemNotificationBinding
import com.maad.jobmarket.home_notification_Nahid.data.model.Notification

class NotificationAdapter (private var notificationList : List<Notification>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),
            parent,false

        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int
    ) {
        val notification = notificationList[position]

        holder.binding.nfTittle.text = notification.title
        holder.binding.nfMassage.text = notification.message
        holder.binding.nfTime.text = notification.time

    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotification(newNotify: List<Notification>){
        notificationList = newNotify.toMutableList()
        notifyDataSetChanged()

    }


    inner class NotificationViewHolder(var binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)
}