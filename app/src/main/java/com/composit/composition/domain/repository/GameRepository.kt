package com.composit.composition.domain.repository

import com.composit.composition.domain.entities.GameSettings
import com.composit.composition.domain.entities.Level
import com.composit.composition.domain.entities.Question

interface GameRepository {
    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}