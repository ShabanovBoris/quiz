package com.rsschool.quiz.ui.pager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding

typealias QuizBundle = Map<String, List<String>>

/**
 * @param _binding will inflate our views
 * and will die in onDestroy
 */
class ViewPagerRecyclerAdapter(
    private var _binding: FragmentQuizBinding?
) : RecyclerView.Adapter<ViewPagerRecyclerAdapter.BasicViewHolder>() {

    private val binding get() = requireNotNull(_binding)
    private var quizData: QuizBundle? = null

    private val types = ColorViewType.values()

    fun setQuizData(map: QuizBundle) {
        quizData = map
    }


    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> ColorViewType.FIRST.ordinal
            1 -> ColorViewType.SECOND.ordinal
            2 -> ColorViewType.THIRD.ordinal
            3 -> ColorViewType.FOURTH.ordinal
            4 -> ColorViewType.FIFTH.ordinal
            else -> throw IllegalArgumentException("invalid position")
        }

    inner class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val question = quizData?.keys?.toList()?.get(position)
            binding.question.text = question
            binding.optionOne.text = quizData?.get(question)?.get(0)
            binding.optionTwo.text = quizData?.get(question)?.get(1)
            binding.optionThree.text = quizData?.get(question)?.get(2)
            binding.optionFour.text = quizData?.get(question)?.get(3)
            binding.optionFive.text = quizData?.get(question)?.get(4)

            binding.toolbar.title = "Question ${position +1}"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        _binding = FragmentQuizBinding
            .inflate(
                // set theme in the new view
                LayoutInflater.from(parent.context).apply {
                    context.theme.applyStyle(types[viewType].styleId, true)
                },
                parent,
                false
            )
        return BasicViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = quizData?.size ?: 0


    enum class ColorViewType(@StyleRes val styleId: Int) {
        FIRST(R.style.Theme_Quiz_First),
        SECOND(R.style.Theme_Quiz_Second),
        THIRD(R.style.Theme_Quiz_Third),
        FOURTH(R.style.Theme_Quiz_Fourth),
        FIFTH(R.style.Theme_Quiz_Fifth);

        override fun toString(): String = styleId.toString()
    }
}