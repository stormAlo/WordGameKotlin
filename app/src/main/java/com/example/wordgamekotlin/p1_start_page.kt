package com.example.wordgamekotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class p1_start_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_p1_start_page)

        val startGame: Button = findViewById(R.id.button)

        startGame.setOnClickListener {
            val intent = Intent(this, p2_menu::class.java)
            startActivity(intent)
        }
    }
}