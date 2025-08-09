package com.maad.jobmarket.presentation.startNav.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maad.jobmarket.R
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentLoginBinding
import com.maad.jobmarket.domain.model.UserAuthModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun setListener() {
        binding.apply {
            tvNavForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }

            btnLogin.setOnClickListener {
                handleLogin()
            }

            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun handleLogin() {
        val userEmail = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (userEmail.isNotEmpty() && password.isNotEmpty()) {
            loginViewModel.signIn(UserAuthModel(email = userEmail, password = password))
        } else {
            Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun allObserver() {
        loginObserver()
    }


    private fun loginObserver() {
        lifecycleScope.launch {
            loginViewModel.signInState.collect { loginState ->
                if (loginState.loading) {
                    loading.show()
                }
                if (loginState.error != null) {
                    loading.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "${loginState.error} Please check email/password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (loginState.data != null) {
                    loading.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "login success",
                        Toast.LENGTH_SHORT
                    ).show()

                    /*startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()*/
                }
            }
        }
    }
}