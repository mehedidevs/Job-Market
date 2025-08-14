package com.maad.jobmarket.Shimon_MockTestQuestion.Data

import com.maad.jobmarket.Shimon_MockTestQuestion.Domain.Model.MockQuestion
import com.maad.jobmarket.Shimon_MockTestQuestion.Domain.Repo.MockQuestionRepository

class MockQuestionRepositoryImpl : MockQuestionRepository {
    override fun getMockQuestions(): List<MockQuestion> {
        return listOf(
            MockQuestion("Which keyword is used to declare a variable that can be reassigned in Kotlin?", "val", "let", "var", "const", "var"),
            MockQuestion("What is the entry point of a Kotlin program?", "init()", "start()", "main()", "launch()", "main()"),
            MockQuestion("Which function is used to print to the console in Kotlin?", "System.out.println()", "echo()", "cout<<", "println()", "println()"),
            MockQuestion("Which of the following is a Kotlin data type?", "Float", "double", "Decimal", "Number", "Float"),
            MockQuestion("What is the correct way to declare a read-only list in Kotlin?", "val list = listOf(1, 2, 3)", "val list = arrayListOf(1, 2, 3)", "var list = mutableListOf(1, 2, 3)", "val list = new ArrayList(1, 2, 3)", "val list = listOf(1, 2, 3)")
        )
    }
}
