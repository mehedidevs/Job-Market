package com.maad.jobmarket.presentation.startNav.forgetPassword

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentForgetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment :
    BaseFragment<FragmentForgetPasswordBinding>(FragmentForgetPasswordBinding::inflate) {
    private val viewModel: ForgetPasswordViewModel by viewModels()
    override fun setListener() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.resetPassword(email)
            } else {
                binding.etEmail.error = "Please enter your email"
            }
        }

    }

    override fun allObserver() {
        resetPasswordObserver()
    }

    private fun resetPasswordObserver() {
        lifecycleScope.launch {
            viewModel.resetPasswordState.collect { response ->
                if (response.loading) {
                    loading.show()
                }
                if (response.error != null) {
                    loading.dismiss()
                    Toast.makeText(
                        requireContext(),
                        response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (response.data != null) {
                    loading.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "Check your email to reset your password",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().onBackPressed()
                }
            }
        }
    }
}