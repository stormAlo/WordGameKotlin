package com.example.wordgamekotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class p2_menu : AppCompatActivity() {

    private lateinit var dbHelper: WordDatabaseHelper
    private var currentScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_p2_menu)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing
            }
        })

        val part1Button: Button = findViewById(R.id.part1btn)
        val part2Button: Button = findViewById(R.id.part2btn)
        val part3Button: Button = findViewById(R.id.part3btn)
        val part4Button: Button = findViewById(R.id.part4btn)
        val resetButton: Button = findViewById(R.id.button2)

//        dbHelper = WordDatabaseHelper(this)

        part1Button.setOnClickListener {
            val intent = Intent(this, P3PartOneMenu::class.java)
            startActivity(intent)
            finish()
        }

//        part2Button.setOnClickListener {
//            val intent = Intent(this, P3PartTwoMenu::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        part3Button.setOnClickListener {
//            val intent = Intent(this, P3PartThreeMenu::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        part4Button.setOnClickListener {
//            val intent = Intent(this, P3PartFourMenu::class.java)
//            startActivity(intent)
//            finish()
//        }

//        resetButton.setOnClickListener {
//            dbHelper.resetDatabase()
//            Toast.makeText(this, "App has been reset!", Toast.LENGTH_SHORT).show()
//            finish()
//        }
//
//        val scoreManager = ScoreManager.getInstance()
//        currentScore = dbHelper.getScore()
//        scoreManager.setScore(currentScore)
//
//        val score = dbHelper.getScore()
//        Toast.makeText(this, "Current Score: $score", Toast.LENGTH_SHORT).show()
//
//        when {
//            currentScore < 10 -> {
//                part2Button.isEnabled = false
//                part3Button.isEnabled = false
//                part4Button.isEnabled = false
//                part2Button.alpha = 0.5f
//                part3Button.alpha = 0.5f
//            }
//            currentScore in 10..19 -> {
//                part2Button.isEnabled = true
//                part2Button.alpha = 1f
//            }
//            currentScore in 20..29 -> {
//                part2Button.isEnabled = true
//                part2Button.alpha = 1f
//                part3Button.isEnabled = true
//                part3Button.alpha = 1f
//            }
//            currentScore >= 30 -> {

    //            }
//        }

    }
}