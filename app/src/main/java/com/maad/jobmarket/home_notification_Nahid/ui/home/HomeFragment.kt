package com.maad.jobmarket.home_notification_Nahid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maad.jobmarket.R
import com.maad.jobmarket.databinding.FragmentHomeBinding
import com.maad.jobmarket.home_notification_Nahid.adapter.JobAdapter
import com.maad.jobmarket.home_notification_Nahid.data.repository.JobRepository
import kotlinx.coroutines.Job

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter : JobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        adapter = JobAdapter(emptyList())
        binding.rvRecentJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecentJobs.adapter = adapter

        viewModel.loadJobs()

        observeViewModel()

        return binding.root

    }

    private fun observeViewModel() {
        viewModel.jobs.observe(viewLifecycleOwner) { jobs ->
            adapter.updateJobs(jobs)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}