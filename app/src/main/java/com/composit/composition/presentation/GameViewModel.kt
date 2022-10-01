package com.composit.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.composit.composition.R
import com.composit.composition.data.GameRepositoryImpl
import com.composit.composition.domain.entities.GameResult
import com.composit.composition.domain.entities.GameSettings
import com.composit.composition.domain.entities.Level
import com.composit.composition.domain.entities.Question
import com.composit.composition.domain.usecase.GenerateQuestionUseCase
import com.composit.composition.domain.usecase.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel(){
    private lateinit var settings: GameSettings

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var timer: CountDownTimer? = null


    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }
    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestions()
        updateProgress()
    }
    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestions()
    }
    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswer.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.right_answer_in_stroke),
            countOfRightAnswers,
            settings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= settings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= settings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if(countOfQuestions == 0){
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        } else {
            countOfQuestions++
        }
    }

    private fun getGameSettings() {
        this.settings = getGameSettingsUseCase(level)
        _minPercent.value = settings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            settings.gameTimeInSeconds * MILLIS_IN_SECONDS, MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0) }

            override fun onFinish() { finishGame() }
        }
        timer?.start()
    }

    private fun generateQuestions() {
        _question.value = generateQuestionUseCase(settings.maxSumValue)
    }


    private fun formatTime(p0: Long): String {
        val seconds = p0 / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }



    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswers.value == true
                    && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfQuestions,
            gameSettings = settings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}