package com.rsschool.quiz.data

class SimpleDataSource {
    fun fetchData() = mapOf(
        "Language which nowadays used for writing code for android?" to listOf<String>(
            "Java", "Kotlin", "C++", "English", "Python"
        ),
        "What do the color theme you see?" to listOf<String>(
            "Blue", "Red", "Yellow", "don't know", "pineapple"
        ),
        "How match answers should be in each question?" to listOf<String>(
            "0 to 5", "at least 5", "0x00101", "only FIVE", "Pyat'"
        ),
        "手には何本の指がありますか ?" to listOf<String>(
            "五", "二", "三", "WTF?", "四"
        ),
        "2 + 2 * 2?" to listOf<String>(
            "6", "222", "8", "Is that question?", "pineapple"
        )
    )

    fun getRightAns() = listOf<Int>(2,1,2,4,1)
}