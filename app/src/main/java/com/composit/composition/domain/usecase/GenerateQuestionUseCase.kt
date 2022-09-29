package com.composit.composition.domain.usecase

import com.composit.composition.domain.entities.Question
import com.composit.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {
    // инвоук для того чтобы в любом месте где использую юскейс - можно будет вызвать его как метод
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}