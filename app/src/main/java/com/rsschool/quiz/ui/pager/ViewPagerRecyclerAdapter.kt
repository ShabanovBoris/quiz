package com.rsschool.quiz.ui.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.ui.utils.MenuManager

//simplify type
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

    //Store values here for don't call values() every time
    private val types = ColorViewType.values()
    //Helper for menu settings
    private val menuManager = MenuManager()

    //listeners
    private var onChecked: ((checkedPosition: Int, page: Int) -> Unit)? = null
    private var onSubmit: (() -> Unit)? = null
    private var onNavigate: ((currentPosition: Int, isNext: Boolean) -> Unit)? = null

    fun setQuizData(map: QuizBundle) {
        quizData = map
    }

    fun addOnCheckedListener(action: (checkedPosition: Int, page: Int) -> Unit) {
        onChecked = action
    }

    fun addOnSubmitListener(action: () -> Unit) {
        onSubmit = action
    }

    fun addOnNavigateListener(action: (currentPosition: Int, isNext: Boolean) -> Unit) {
        onNavigate = action
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

    inner class BasicViewHolder(private val bindingItem: FragmentQuizBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {

        fun bind(position: Int) {
            val currentQuestion = quizData?.keys?.toList()?.get(position)
            with(bindingItem) {
                menuManager.handleMenuItemClick(bindingItem.toolbar, bindingItem.toolbar.context)
                /**
                 * set views
                 */
                question.text = currentQuestion
                optionOne.text = quizData?.get(currentQuestion)?.get(0)
                optionTwo.text = quizData?.get(currentQuestion)?.get(1)
                optionThree.text = quizData?.get(currentQuestion)?.get(2)
                optionFour.text = quizData?.get(currentQuestion)?.get(3)
                optionFive.text = quizData?.get(currentQuestion)?.get(4)

                toolbar.title = "Question ${position + 1}"
                //if there is haven't checked buttons
                if (radioGroup.checkedRadioButtonId == -1) {
                    nextButton.isEnabled = false
                }

                //handle radio buttons changes
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    nextButton.isEnabled = true
                    //the passed lambda calls
                    onChecked?.invoke(
                        group.indexOfChild(group.findViewById(checkedId)),
                        position
                    )
                }
                //Navigation click listeners
                nextButton.setOnClickListener {
                    onNavigate?.invoke(position, true)
                }
                previousButton.setOnClickListener {
                    onNavigate?.invoke(position, false)
                }
                toolbar.setNavigationOnClickListener {
                    onNavigate?.invoke(position, false)
                }
                //last screen settings
                if (position == itemCount - 1) {
                    nextButton.text = "submit"
                    nextButton.setOnClickListener {
                        onSubmit?.invoke()
                    }
                }
                //first screen settings
                if (position == 0) {
                    toolbar.navigationIcon = null
                    previousButton.isEnabled = false
                }

            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        _binding = FragmentQuizBinding
            .inflate(
                // set theme into new view
                LayoutInflater.from(parent.context.apply {
                    theme.applyStyle(types[viewType].styleId, true)
                }),
                parent,
                false
            )

    return BasicViewHolder(binding)
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