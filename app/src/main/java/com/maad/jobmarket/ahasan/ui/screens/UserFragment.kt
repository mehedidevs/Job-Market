package com.maad.jobmarket.ahasan.ui.screens


import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maad.jobmarket.R
import com.maad.jobmarket.ahasan.ui.viewmodels.StudentViewModel
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentUserBinding
import kotlin.getValue

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val studentViewModel by viewModels<StudentViewModel>()
    val id = "TEST001"

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
        observeStudent()
        observeResume()
    }


    private fun observeStudent() {
        lifecycleScope.launchWhenStarted {
            studentViewModel.studentState.collect { state ->
                if (state.loading) loading.show()
                if (state.error != null) {
                    loading.dismiss()
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
                state.student?.let { student ->
                    loading.dismiss()
                    binding.tvUsername.text = student.details?.username.orEmpty()
                    binding.tvUserEmail.text = student.details?.email.orEmpty()
                    binding.cvManageAccount.setOnClickListener {
                        findNavController().navigate(
                            UserFragmentDirections.actionUserFragmentToUserEditFragment(student)
                        )
                    }
                }
            }
        }
    }

    private fun observeResume() {
        lifecycleScope.launchWhenStarted {
            studentViewModel.resumeState.collect { state ->
                if (state.loading) Toast.makeText(requireContext(), "Loading resume...", Toast.LENGTH_SHORT).show()
                state.resume?.let { (fileName, meta, uri) ->
                    Toast.makeText(requireContext(), "Resume: $fileName", Toast.LENGTH_SHORT).show()
                }
                state.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            }
        }
    }













}