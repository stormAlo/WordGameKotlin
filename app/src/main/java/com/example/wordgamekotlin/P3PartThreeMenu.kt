package com.example.wordgamekotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback


class P3PartThreeMenu : AppCompatActivity() {

    private val requiredScores = intArrayOf(20, 21, 22, 23, 24, 25, 26, 27, 28, 29)
    private var currentScore: Int = 0
    private lateinit var scoreTextView: TextView
    private lateinit var dbHelper: WordDatabaseHelper
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_p3_part_three_menu)

        backBtn = findViewById(R.id.backButton)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@P3PartThreeMenu, "برای بازگشت به صفحه قبل از دکمه بازگشت استفاده کنید", Toast.LENGTH_SHORT).show()
            }
        })

        backBtn.setOnClickListener {
            val intent = Intent(this, p2_menu::class.java)
            startActivity(intent)
            finish()
        }

        dbHelper = WordDatabaseHelper(this)

        scoreTextView = findViewById(R.id.scoreTextView)
        updateScoreDisplay()

        val buttons = arrayOfNulls<Button>(10)
        buttons[0] = findViewById(R.id.stage1)
        buttons[1] = findViewById(R.id.stage2)
        buttons[2] = findViewById(R.id.stage3)
        buttons[3] = findViewById(R.id.stage4)
        buttons[4] = findViewById(R.id.stage5)
        buttons[5] = findViewById(R.id.stage6)
        buttons[6] = findViewById(R.id.stage7)
        buttons[7] = findViewById(R.id.stage8)
        buttons[8] = findViewById(R.id.stage9)
        buttons[9] = findViewById(R.id.stage10)

        for (i in currentScore - 20 until buttons.size) {
            val stageIndex = i
            if (currentScore < requiredScores[i]) {
                buttons[i]?.alpha = 0.5f
                buttons[i]?.isEnabled = false
            } else {
                buttons[i]?.alpha = 1.0f
                buttons[i]?.isEnabled = true
            }
            buttons[i]?.setOnClickListener {
                if (currentScore < requiredScores[stageIndex]) {
                    Toast.makeText(this@P3PartThreeMenu, "امتیاز شما برای ورود به این مرحله کافی نیست. ابتدا مرحله ${currentScore + 1} را حل کنید", Toast.LENGTH_SHORT).show()
                } else {
                    val levelNumber = stageIndex
                    val intent = Intent(this, P4MainGame::class.java)
                    intent.putExtra("LEVEL_NUMBER", levelNumber)
                    intent.putExtra("STAGE_NUMBER", 3)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateScoreDisplay()
    }

    private fun updateScoreDisplay() {
        currentScore = ScoreManager.getInstance().score // Accessing the score property directly
        scoreTextView.text = "امتیاز: $currentScore"
        dbHelper.saveScore(currentScore)
        if (currentScore == 30) {
            Toast.makeText(this, "شما با موفقیت مرحله سوم را پشت سر گذاشتید! فوق العاده بود", Toast.LENGTH_SHORT).show()
        }

    }
}