package com.composit.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.composit.composition.R
import com.composit.composition.databinding.FragmentGameFinishedBinding


class GameFinishedFragment : Fragment() {
    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()
    }

    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(setCorrectEmoji())
            tvRequiredAnswer.text = String.format(
                getString(R.string.required_answer),
                args.gameResult.gameSettings.minCountOfRightAnswers
            )
            tvRequiredScore.text = String.format(
                getString(R.string.score),
                args.gameResult.countOfRightAnswers
            )
            tvPercentScore.text = String.format(
                getString(R.string.percent),
                args.gameResult.gameSettings.minPercentOfRightAnswers
            )

            tvRealPercent.text = String.format(
                getString(R.string.real_percent),
                getPercentOfRightAnswer()
            )
        }
    }

    private fun setCorrectEmoji(): Int {
        return if (args.gameResult.winner) {
            R.drawable.img3
        } else {
            R.drawable.img4
        }
    }

    private fun getPercentOfRightAnswer() = with(args.gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}