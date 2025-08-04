package com.maad.jobmarket.presentation.startNav.forgetPassword

import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentForgetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment :
    BaseFragment<FragmentForgetPasswordBinding>(FragmentForgetPasswordBinding::inflate) {
    override fun setListener() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }

    override fun allObserver() {
        //
    }
}