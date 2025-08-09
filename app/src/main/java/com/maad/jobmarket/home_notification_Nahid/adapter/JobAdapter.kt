package com.maad.jobmarket.home_notification_Nahid.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maad.jobmarket.databinding.ItemJobBinding
import com.maad.jobmarket.home_notification_Nahid.data.model.Job


class JobAdapter(
    private var jobsList: List<Job>)
    : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder( var binding: ItemJobBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
       val job = jobsList[position]

        holder.binding.tvJobTitle.text = job.title
        holder.binding.tvCompanyLocation.text = job.company
        holder.binding.tvSalary.text = job.salary
        holder.binding.tvJobType.text = job.role
        holder.binding.tvEmploymentType.text = job.location

    }

    override fun getItemCount(): Int {
        return jobsList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateJobs(newJobs: List<Job>) {
        jobsList = newJobs.toMutableList()
        notifyDataSetChanged()
    }
}





