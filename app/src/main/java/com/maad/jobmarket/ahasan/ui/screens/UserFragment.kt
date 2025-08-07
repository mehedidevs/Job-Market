package com.maad.jobmarket.ahasan.ui.screens


import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maad.jobmarket.R
import com.maad.jobmarket.ahasan.data.model.Student
import com.maad.jobmarket.ahasan.ui.viewmodels.UserEditViewModel
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.core.utils.Resource
import com.maad.jobmarket.databinding.FragmentUserBinding
import kotlin.getValue

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val userEditViewModel by viewModels<UserEditViewModel>()

    override fun setListener() {
        binding.cvManageAccount.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_userEditFragment)
        }
        binding.cvUpdateResume.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_userResumeEdit)
        }

        binding.cvContactTpo.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_userTpoContact)
        }
        binding.ivPopOut.setOnClickListener {
            findNavController().navigateUp()

        }
        binding.cvLogout.setOnClickListener { }


    }

    override fun allObserver() {
        setupObserver()
    }


    private fun setupObserver() {
        userEditViewModel.student.observe(viewLifecycleOwner) { studentState ->
            when (studentState) {
                is Resource.Success<*> -> {
                    val student = studentState.data as Student
                    binding.tvUsername.text = student.details?.username
                    binding.tvUserEmail.text = student.details?.email

                    binding.cvManageAccount.setOnClickListener {
                        val action = UserFragmentDirections.actionUserFragmentToUserEditFragment(student)
                        findNavController().navigate(action)
                    }

                    binding.cvUpdateResume.setOnClickListener {
                        Toast.makeText(requireContext(), "Resume Clicked: ${student.academic?.resumeUrl}", Toast.LENGTH_SHORT).show()
                    }
                    binding.cvContactTpo.setOnClickListener {
                        findNavController().navigate(R.id.action_userFragment_to_userTpoContact)
                    }
                }

                is Resource.Error<*> -> {
                    Toast.makeText(requireContext(), studentState.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading<*> -> {
                    Toast.makeText(requireContext(), "Loading dummy data...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }







}