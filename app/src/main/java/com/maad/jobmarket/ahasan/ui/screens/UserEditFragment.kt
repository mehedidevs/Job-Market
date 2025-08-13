package com.maad.jobmarket.ahasan.ui.screens



import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maad.jobmarket.ahasan.ui.viewmodels.UserEditViewModel
import com.maad.jobmarket.core.utils.BaseFragment
import com.maad.jobmarket.databinding.FragmentUserEditBinding
import kotlin.getValue

class UserEditFragment : BaseFragment<FragmentUserEditBinding>(FragmentUserEditBinding::inflate) {

    private val args by navArgs<UserEditFragmentArgs>()
    private val userEditViewModel by viewModels<UserEditViewModel>()



    override fun setListener() {
        binding.ivPopOut.setOnClickListener {
            findNavController().navigateUp()
        }
        return
    }

    override fun allObserver() {
        return

    }

}