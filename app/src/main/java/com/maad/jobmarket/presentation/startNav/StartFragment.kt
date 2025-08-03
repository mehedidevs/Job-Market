package com.maad.jobmarket.presentation.startNav

import androidx.navigation.fragment.findNavController
import com.maad.jobmarket.R
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    override fun setListener() {

        binding.apply {
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registerFragment)
            }
        }
    }

    override fun allObserver() {
        //
    }

}