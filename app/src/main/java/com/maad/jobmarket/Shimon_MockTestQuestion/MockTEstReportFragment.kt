package com.maad.jobmarket.Shimon_MockTestQuestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maad.jobmarket.databinding.FragmentMockTEstReportBinding


class MockTEstReportFragment : Fragment() {

    lateinit var binding: FragmentMockTEstReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMockTEstReportBinding.inflate(layoutInflater,container,false)

        val skip = requireArguments().getString("skip", "0")!!.toInt()
        val correct = requireArguments().getString("right", "0")!!.toInt()
        val wrong = requireArguments().getString("wrong", "0")!!.toInt()
        val numOfQuestion = requireArguments().getString("numOfQuestion", "0")!!.toInt()
        val timeLeft = arguments?.getString("timeLeft") ?: "0"

        binding.correct.text = "Correct: $correct / $numOfQuestion"
        binding.progressText.text = "$correct / $numOfQuestion"
        binding.textIncorrect.text = "Wrong: $wrong"
        binding.skip.text = "Skipped: $skip"
        binding.timeTaken.text = "Time Left: $timeLeft seconds"


        // Calculate percentage and set to progress bar
        val percentage = (correct * 100) / numOfQuestion
        binding.circularProgressBar.progress = percentage


        return binding.root
    }

}