package com.maad.jobmarket.Shimon_MockTestQuestion.Presentation.MockTestQuestion

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maad.jobmarket.R
import com.maad.jobmarket.databinding.FragmentMockTestQuestionBinding
import kotlinx.coroutines.flow.collectLatest

class MockTestQuestionFragment : Fragment() {

    private lateinit var binding: FragmentMockTestQuestionBinding
    private val viewModel: MockTestQuestionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMockTestQuestionBinding.inflate(inflater, container, false)

        setupObservers()
        setupClickListeners()
        return binding.root
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                // Set question and options
                binding.apply {
                    questionTV.text = state.currentQuestion?.question
                    option1.text = state.currentQuestion?.option1
                    option2.text = state.currentQuestion?.option2
                    option3.text = state.currentQuestion?.option3
                    option4.text = state.currentQuestion?.option4
                    // Update UI
                    totalQuestionTV.text = "Question: ${state.currentIndex + 1}/${state.totalQuestions}"
                    totalScoreTV.text = "Score: ${state.score}"
                    timerText.text = "Time Left: ${state.timeLeftInSec}"
                }


                // Highlight selected answer
                updateCardSelection(state.selectedAnswer)

                // Navigate to result screen
                if (state.quizFinished) {
                    val bundle = Bundle().apply {
                        putString("skip", state.skip.toString())
                        putString("right", state.correct.toString())
                        putString("wrong", state.wrong.toString())
                        putString("numOfQuestion", state.totalQuestions.toString())
                        putString("timeLeft", state.timeLeftInSec.toString())
                    }
                    findNavController().navigate(R.id.action_mockTestQuestionFragment_to_mockTEstReportFragment, bundle)
                }
            }
        }
    }
    private fun setupClickListeners() {
        listOf(
            binding.card1 to binding.option1,
            binding.card2 to binding.option2,
            binding.card3 to binding.option3,
            binding.card4 to binding.option4
        ).forEach { (card, optionView) ->
            card.setOnClickListener {
                viewModel.selectAnswer(optionView.text.toString())
            }
        }

        binding.nextBtn.setOnClickListener { viewModel.nextQuestion() }
    }


    private fun updateCardSelection(selected: String?) {
        val defaultColor = ContextCompat.getColor(requireContext(), android.R.color.white)
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.secondaryColor)

        // Reset all cards to default
        binding.apply {
            card1.setCardBackgroundColor(defaultColor)
            card2.setCardBackgroundColor(defaultColor)
            card3.setCardBackgroundColor(defaultColor)
            card4.setCardBackgroundColor(defaultColor)

        }

        // Highlight selected card
        when (selected) {
            binding.option1.text.toString() -> binding.card1.setCardBackgroundColor(selectedColor)
            binding.option2.text.toString() -> binding.card2.setCardBackgroundColor(selectedColor)
            binding.option3.text.toString() -> binding.card3.setCardBackgroundColor(selectedColor)
            binding.option4.text.toString() -> binding.card4.setCardBackgroundColor(selectedColor)
        }
    }
}
