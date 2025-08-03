package com.maad.jobmarket.presentation.startNav.signup

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maad.jobmarket.R
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentRegisterBinding
import com.maad.jobmarket.domain.model.UserAuthModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val signUpViewModel: SignUpViewModel by viewModels()
    override fun setListener() {

        binding.apply {
            btnSignup.setOnClickListener {
                handleSignup()
            }

            llNavLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun handleSignup() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            val signupRequest = UserAuthModel(
                email = email,
                password = password
            )
            signUpViewModel.signUp(signupRequest)
        } else{
            Toast.makeText(
                requireContext(),
                "Please fill all fields correctly!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun allObserver() {
        signupObserver()
    }

    private fun signupObserver() {
        lifecycleScope.launch {
            signUpViewModel.signUpState.collect { signupState ->
                if (signupState.loading) {
                    loading.show()
                }
                if (signupState.data != null) {
                    loading.dismiss()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                if (signupState.error != null) {
                    loading.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "${signupState.error} Something went wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}