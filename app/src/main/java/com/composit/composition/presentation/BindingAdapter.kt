package com.composit.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.composit.composition.R
import com.composit.composition.domain.entities.GameResult

@BindingAdapter("requiredAnswer")
fun bindRequiredAnswer(textView: TextView,count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_answer),
        count
    )
}// все правильно
@BindingAdapter("score")
fun bindRequiredScore(textView: TextView,count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.score),
        count
    )
} // все правильно

@BindingAdapter("percent_of_Right")
fun bindPercentReal(textView: TextView,percent: Int){
    textView.text = String.format(
        textView.context.getString(R.string.percent),
        percent
    )
}
@BindingAdapter("percent")
fun bindPercentScore(textView: TextView,gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.real_percent),
        getPercentOfRightAnswer(gameResult)
    )
}

private fun getPercentOfRightAnswer(gameResult:GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("emoji")
fun bindEmoji(imageView: ImageView,count: Boolean){
    when(count){
        true -> imageView.setImageResource(R.drawable.img3)
        false -> imageView.setImageResource(R.drawable.img4)
    }

}