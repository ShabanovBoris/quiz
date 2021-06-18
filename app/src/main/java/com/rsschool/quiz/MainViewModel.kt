package com.rsschool.quiz

import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.SimpleDataSource
import com.rsschool.quiz.ui.pager.QuizBundle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val repository = SimpleDataSource()
    private val rightAnswers by lazy(LazyThreadSafetyMode.NONE) {
        repository.getRightAns()
    }
    private val currentAnswers = mutableMapOf<Int, Int>()

    private var _state: MutableStateFlow<Result> = MutableStateFlow(Result.EmptyState())
    val state get() = _state.asStateFlow()

    private var _data: MutableSharedFlow<QuizBundle> = MutableSharedFlow(1)
    val data get() = _data.asSharedFlow()

    init {
        if (_data.tryEmit(repository.fetchData())) {
            _state.value = Result.EmptyState()
        } else {
            _state.value = Result.Error()
        }
    }

    fun putAnswer(page: Int, ans: Int) {
        if (rightAnswers[page]-1 == ans) {
            currentAnswers.put(page, 1)
        } else {
            currentAnswers.put(page, 0)
        }
        _state.value = Result.Success(currentAnswers)
    }
}


sealed class Result() {
    class Success(val resultMap: Map<Int, Int>) : Result()
    class Error() : Result()
    class EmptyState() : Result()
}