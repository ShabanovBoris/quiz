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
    private val _currentAnswers = mutableMapOf<Int, Int>()
    val currentAnswers: Map<Int, Int> get() = _currentAnswers

    private var statistic = ""

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
            _currentAnswers.put(page, 1)
        } else {
            _currentAnswers.put(page, 0)
        }
        _state.value = Result.Success(_currentAnswers)


        val quests = data.replayCache[0].keys.toList()
        val anses = data.replayCache[0].values.toList()
        statistic += "${quests[page]}\nYour answer: ${anses[page][ans]}\n "
    }

    fun getStatistic() = statistic

    fun clearStatistic() {
        statistic = ""
        _currentAnswers.clear()
    }
}


sealed class Result() {
    class Success(val resultMap: Map<Int, Int>) : Result()
    class Error() : Result()
    class EmptyState() : Result()
}