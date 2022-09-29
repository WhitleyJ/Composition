package com.composit.composition.domain.entities

data class Question (
    val sum: Int, // значение суммы в кружке
    val visibleNumber: Int, //видимое число,которое отображается в левом квадрате
    val options: List<Int> // варианты ответов
    )