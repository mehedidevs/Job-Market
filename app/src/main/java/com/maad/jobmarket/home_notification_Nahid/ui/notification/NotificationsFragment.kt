package com.maad.jobmarket.home_notification_Nahid.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maad.jobmarket.R
import com.maad.jobmarket.databinding.FragmentHomeBinding
import com.maad.jobmarket.databinding.FragmentNotificationsBinding
import com.maad.jobmarket.home_notification_Nahid.adapter.NotificationAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.notifications.observe(viewLifecycleOwner){ list ->
            adapter.updateNotification(list)
        }

    }

    private fun setupRecyclerView() {
        adapter = NotificationAdapter(emptyList())
        binding.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotification.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }


}