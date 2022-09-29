package com.composit.composition.domain.entities

data class GameSettings (
    val maxSumValue: Int, //максимально возможное значение суммы
    val minCountOfRightAnswers : Int, // минимальное количество правильных ответов для победы
    val minPercentOfRightAnswers: Int, // минимальный процент правильных ответов
    val gameTimeInSeconds: Int // время игры в секундах
    )