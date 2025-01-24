package com.example.wordgamekotlin

class ScoreManager private constructor() {
    var score: Int = 0
        private set

    companion object {
        @Volatile
        private var instance: ScoreManager? = null

        fun getInstance(): ScoreManager {
            return instance ?: synchronized(this) {
                instance ?: ScoreManager().also { instance = it }
            }
        }
    }

    fun setScore(score: Int) {
        this.score = score
    }
}