package com.composit.composition.domain.usecase

import com.composit.composition.domain.entities.GameSettings
import com.composit.composition.domain.entities.Level
import com.composit.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}