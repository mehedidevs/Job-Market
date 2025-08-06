package com.maad.jobmarket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maad.jobmarket.domain.model.MockQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class QuizUiState(
    val currentQuestion: MockQuestion? = null,
    val currentIndex: Int = 0,
    val totalQuestions: Int = 0,
    val selectedAnswer: String? = null,
    val correct: Int = 0,
    val wrong: Int = 0,
    val skip: Int = 0,
    val score: Int = 0,
    val timeLeftInSec: Int = 60,
    val quizFinished: Boolean = false
)

class MockTestQuestionViewModel : ViewModel() {

    private val mockQuestionList = listOf(
        MockQuestion("Which keyword is used to declare a variable that can be reassigned in Kotlin?", "val", "let", "var", "const", "var"),
        MockQuestion("What is the entry point of a Kotlin program?", "init()", "start()", "main()", "launch()", "main()"),
        MockQuestion("Which function is used to print to the console in Kotlin?", "System.out.println()", "echo()", "cout<<", "println()", "println()"),
        MockQuestion("Which of the following is a Kotlin data type?", "Float", "double", "Decimal", "Number", "Float"),
        MockQuestion("What is the correct way to declare a read-only list in Kotlin?", "val list = listOf(1, 2, 3)", "val list = arrayListOf(1, 2, 3)", "var list = mutableListOf(1, 2, 3)", "val list = new ArrayList(1, 2, 3)", "val list = listOf(1, 2, 3)")
    )

    private val _uiState = MutableStateFlow(QuizUiState(
        currentQuestion = mockQuestionList.first(),
        totalQuestions = mockQuestionList.size
    ))
    val uiState: StateFlow<QuizUiState> = _uiState

    private var timerRunning = true

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_uiState.value.timeLeftInSec > 0 && timerRunning) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    timeLeftInSec = _uiState.value.timeLeftInSec - 1
                )
            }
            if (_uiState.value.timeLeftInSec == 0) {
                finishQuiz()
            }
        }
    }

    fun selectAnswer(answer: String) {
        _uiState.value = _uiState.value.copy(selectedAnswer = answer)
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        val selected = currentState.selectedAnswer
        val correctAnswer = currentState.currentQuestion?.answer

        val (correct, wrong, skip) = when {
            selected == null -> Triple(currentState.correct, currentState.wrong, currentState.skip + 1)
            selected == correctAnswer -> Triple(currentState.correct + 1, currentState.wrong, currentState.skip)
            else -> Triple(currentState.correct, currentState.wrong + 1, currentState.skip)
        }

        val nextIndex = currentState.currentIndex + 1

        if (nextIndex < mockQuestionList.size) {
            _uiState.value = currentState.copy(
                currentIndex = nextIndex,
                currentQuestion = mockQuestionList[nextIndex],
                selectedAnswer = null,
                correct = correct,
                wrong = wrong,
                skip = skip,
                score = correct
            )
        } else {
            _uiState.value = currentState.copy(
                correct = correct,
                wrong = wrong,
                skip = skip,
                score = correct,
                quizFinished = true
            )
            timerRunning = false
        }
    }

    fun finishQuiz() {
        timerRunning = false
        _uiState.value = _uiState.value.copy(quizFinished = true)
    }
}
