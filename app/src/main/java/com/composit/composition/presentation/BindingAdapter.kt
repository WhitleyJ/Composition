package com.composit.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.composit.composition.R
import com.composit.composition.domain.entities.GameResult

@BindingAdapter("requiredAnswer")
fun bindRequiredAnswer(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_answer),
        count
    )
}// все правильно

@BindingAdapter("score")
fun bindRequiredScore(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score),
        count
    )
} // все правильно

@BindingAdapter("percent_of_Right")
fun bindPercentReal(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.percent),
        percent
    )
}

@BindingAdapter("percent")
fun bindPercentScore(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.real_percent),
        getPercentOfRightAnswer(gameResult)
    )
}

private fun getPercentOfRightAnswer(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("emoji")
fun bindEmoji(imageView: ImageView, count: Boolean) {
    when (count) {
        true -> imageView.setImageResource(R.drawable.img3)
        false -> imageView.setImageResource(R.drawable.img4)
    }

}

@BindingAdapter("enoughCount")
fun bindColor(textView: TextView, enough: Boolean) {
    textView.setTextColor(parsColor(textView.context, enough))

}

@BindingAdapter("enoughPercent")
fun bindColorProgress(progressBar: ProgressBar, enough: Boolean) {
    val color = parsColor(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)

}

private fun parsColor(context: Context, goodState: Boolean): Int {
    val color = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    val textColor = ContextCompat.getColor(context, color)
    return textColor
}

@BindingAdapter("numberToText")
fun numberToText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionItemClick")
fun bindOnClickOption(textView: TextView,clickListener: OnOptionItemClick){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }

}

interface OnOptionItemClick{
    fun onOptionClick(option: Int)
}

