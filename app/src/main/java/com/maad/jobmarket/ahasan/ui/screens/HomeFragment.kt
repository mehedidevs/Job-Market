package com.maad.jobmarket.ahasan.ui.screens

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maad.jobmarket.R
import com.maad.jobmarket.ahasan.data.model.Job
import com.maad.jobmarket.ahasan.ui.adapter.JobAdapter
import com.maad.jobmarket.ahasan.ui.viewmodels.UserEditViewModel
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var jobAdapter: JobAdapter
    private val userEditViewModel by viewModels<UserEditViewModel>()

    override fun setListener() {

        binding.ivProfileImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userFragment)

        }
    }

    override fun allObserver() {
        userEditViewModel.fetchjobs() // Load dummy data

        userEditViewModel.jobs.observe(viewLifecycleOwner) { jobList ->
            setupJobRecycler(jobList)
        }
    }
    private fun setupJobRecycler(jobs: List<Job>) {
        jobAdapter = JobAdapter(jobs) { selectedJob ->

        }

        binding.rvRecentJobs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jobAdapter
        }
    }


}