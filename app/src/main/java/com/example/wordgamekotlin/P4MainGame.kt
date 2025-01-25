package com.example.wordgamekotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import java.util.*

class P4MainGame : AppCompatActivity() {

    private lateinit var dbHelper: WordDatabaseHelper
    private lateinit var wordContainer: GridLayout
    private lateinit var spaceContainer: GridLayout
    private val wordButtons = mutableListOf<Button>()
    private val spaceTextViews = mutableListOf<TextView>()
    private lateinit var thisStageWord: String
    private lateinit var resetButton: Button
    private val characters = mutableListOf<String>()
    private lateinit var levelTextView: TextView
    private var completeWord = StringBuilder()
    private var lastTextView: TextView? = null
    private var levelNumber: Int = 0
    private var stageNumber: Int = 0
    private var liveStage: Int = 5
    private lateinit var heart: Array<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_p4_main_game)


        dbHelper = WordDatabaseHelper(this)
        wordContainer = findViewById(R.id.wordContainer)
        spaceContainer = findViewById(R.id.spaceContainer)
        resetButton = findViewById(R.id.resetButton)
        levelTextView = findViewById(R.id.levelTextView)

        heart = arrayOf(
            findViewById(R.id.heart1img),
            findViewById(R.id.heart2img),
            findViewById(R.id.heart3img),
            findViewById(R.id.heart4img),
            findViewById(R.id.heart5img)
        )

        levelNumber = intent.getIntExtra("LEVEL_NUMBER", 0)
        stageNumber = intent.getIntExtra("STAGE_NUMBER", 0)

        levelTextView.text = "مرحله: ${levelNumber + 1}"

        thisStageWord = when (stageNumber) {
            1 -> dbHelper.makingLists(levelNumber, 1)
            2 -> dbHelper.makingLists(levelNumber, 2)
            3 -> dbHelper.makingLists(levelNumber, 3)
            else -> ""
        }

        loadWords()

        resetButton.setOnClickListener { resetWords() }
    }

    private fun loadWords() {
        for (i in thisStageWord.indices) {
            val currentChar = thisStageWord[i]
            characters.add(currentChar.toString())
        }
        characters.shuffle()
        spaceTextViews.clear()

        wordContainer.removeAllViews()
        spaceContainer.removeAllViews()

        for (character in characters) {
            createWordButton(character)
            createSpaceTextView()
        }
    }

    private fun createSpaceTextView() {
        val wordText = TextView(this).apply {
            text = "___ "
            textSize = 20f
            setTextColor(Color.WHITE)
        }

        val params = GridLayout.LayoutParams().apply {
            width = GridLayout.LayoutParams.WRAP_CONTENT
            height = GridLayout.LayoutParams.WRAP_CONTENT
        }

        wordText.layoutParams = params
        spaceContainer.addView(wordText)
        spaceTextViews.add(wordText)
    }

    private fun createWordButton(word: String) {
        val wordButton = Button(this).apply {
            text = word
            textSize = 16f
            layoutParams = GridLayout.LayoutParams().apply {
                width = (40 * resources.displayMetrics.density).toInt()
                height = GridLayout.LayoutParams.WRAP_CONTENT
            }
            setOnClickListener { replaceWordWithLetter(this) }
        }

        wordContainer.addView(wordButton)
        wordButtons.add(wordButton)
    }

    private fun replaceWordWithLetter(wordButton: Button) {
        val letter = wordButton.text.toString()
        completeWord.append(letter)

        for (spaceTextView in spaceTextViews) {
            if (spaceTextView.text.toString() == "___ ") {
                lastTextView?.let { spaceContainer.removeView(it) }
                spaceTextView.text = completeWord.toString()
                lastTextView = spaceTextView
                break
            }
        }
        wordButton.isClickable = false
        wordButton.alpha = 0.5f

        if (completeWord.length == thisStageWord.length) {
            if (thisStageWord == completeWord.toString()) {
                winningEventelse {
               losingHeart()
          }
        }
    }

    private fun resetWords() {
        wordContainer.removeAllViews()
        spaceContainer.removeAllViews()

        characters.clear()
        completeWord = StringBuilder()
        lastTextView = null

        loadWords()
    }
        private fun losingHeart() {
            liveStage -= 1
            Toast.makeText(this, "اشتباه بود :(( دوباره تلاش کن", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ resetWords() }, 500)
            when (liveStage) {
                5 -> heart.forEach { it.alpha = 1f }
                4 -> {
                    startHeartAnimation(4)
                    heart[4].alpha = 0.5f
                }
                3 -> {
                    startHeartAnimation(3)
                    heart[3].alpha = 0.5f
                }
                2 -> {
                    startHeartAnimation(2)
                    heart[2].alpha = 0.5f
                }
                1 -> {
                    startHeartAnimation(1)
                    heart[1].alpha = 0.5f
                }
                0 -> {
                    startHeartAnimation(0)
                    heart[0].alpha = 0.5f
                    Handler().postDelayed({
                        when (stageNumber) {
                            1 -> {
                                ScoreManager.getInstance().setScore(0)
                                startActivity(Intent(this, P3PartOneMenu::class.java))
                            }
                            2 -> {
                                ScoreManager.getInstance().setScore(10)
                                startActivity(Intent(this, P3PartTwoMenu::class.java))
                            }
                            3 -> {
                                ScoreManager.getInstance().setScore(20)
                                startActivity(Intent(this, P3PartThreeMenu::class.java))
                            }
                        }
                        finish()
                    }, 1000)
                }
            }
        }
        private fun startHeartAnimation(number: Int) {
            val heartAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.heart_animation)
            heart[number].startAnimation(heartAnimation)
        }


    private fun winningEvents() {
    }
        Toast.makeText(this, "آفرین!", Toast.LENGTH_SHORT).show()
        resetButton.isEnabled = false
        resetButton.alpha = 0.5f
        val newScore = ScoreManager.getInstance().score + 1
        ScoreManager.getInstance().setScore(newScore)

        Handler().postDelayed({
            when (stageNumber) {
                1 -> startActivity(Intent(this, P3PartOneMenu::class.java))
                2 -> startActivity(Intent(this, P3PartTwoMenu::class.java))
                3 -> startActivity(Intent(this, P3PartThreeMenu::class.java))
            }
            finish()
        }, 1000)

    }
}