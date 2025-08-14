package com.maad.jobmarket.Shimon_MockTestQuestion.Domain.Repo

import com.maad.jobmarket.Shimon_MockTestQuestion.Domain.Model.MockQuestion

interface MockQuestionRepository {
    fun getMockQuestions(): List<MockQuestion>
}
