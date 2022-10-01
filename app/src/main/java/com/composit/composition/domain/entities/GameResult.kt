package com.composit.composition.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult (
    val winner: Boolean, //значение победили ли мы или нет
    val countOfRightAnswers: Int, // значение правильных ответов
    val countOfQuestions: Int, // общее количество вопросов на которые ответил пользователь
    val gameSettings: GameSettings // настройки игры
    ): Parcelable