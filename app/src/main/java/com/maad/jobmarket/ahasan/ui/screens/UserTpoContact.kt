package com.maad.jobmarket.ahasan.ui.screens


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maad.jobmarket.ahasan.ui.adapter.TpoAdapter
import com.maad.jobmarket.ahasan.ui.viewmodels.UserEditViewModel
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentUserTpoContactBinding

class UserTpoContact : BaseFragment<FragmentUserTpoContactBinding>(FragmentUserTpoContactBinding::inflate) {
    private val tpoAdapter = TpoAdapter()
    private val userEditViewModel by viewModels<UserEditViewModel>()

    override fun setListener() {
    return
    }

    override fun allObserver() {
        setupUI()
        setupObserver()
    }


    private fun setupUI() {
        // Load dummy data
        userEditViewModel.fetchTpo()

        binding.ivPopOut.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvContactTPO.apply {
            adapter = tpoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObserver() {
        userEditViewModel.tpoList.observe(viewLifecycleOwner) { tpoList ->
            tpoAdapter.setData(tpoList)
        }
    }

}