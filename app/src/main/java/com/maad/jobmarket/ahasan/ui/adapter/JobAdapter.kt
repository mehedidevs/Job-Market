package com.maad.jobmarket.ahasan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maad.jobmarket.ahasan.domain.model.Job
import com.maad.jobmarket.databinding.JobItemCardBinding

class JobAdapter( private val jobList: List<Job>, private val onItemClick: (Job) -> Unit ) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder(val binding: JobItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            binding.tvJobRole.text = job.role
            binding.tvCompanyNameLocation.text = "${job.name} • ${job.city}"
            binding.tvSalary.text = job.salary
            binding.chipDesignation.text = job.designation
            binding.chipWorkType.text = job.workType


            binding.btnApply.setOnClickListener {
                onItemClick(job)
            }

            binding.root.setOnClickListener {
                onItemClick(job)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobList[position])
    }

    override fun getItemCount(): Int = jobList.size
}
