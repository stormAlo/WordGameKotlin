package com.example.wordgamekotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WordDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "words.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_WORDS = "words"
        private const val COLUMN_WORD = "word"
        private const val COLUMN_STAGE = "stage"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createScoresTable = """
            CREATE TABLE scores_table (
                id INTEGER PRIMARY KEY,
                score INTEGER
            )
        """.trimIndent()
        db.execSQL(createScoresTable)
        db.execSQL("INSERT INTO scores_table (id, score) VALUES (1, 0)")

        val createTable = """
            CREATE TABLE $TABLE_WORDS (
                $COLUMN_WORD TEXT,
                $COLUMN_STAGE INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)

        // Insert words into the database
        insertWords(db)
    }

    private fun insertWords(db: SQLiteDatabase) {
        val words = listOf(
            // Stage 1
            "سیب" to 1, "موز" to 1, "انگور" to 1, "پرتقال" to 1, "کیوی" to 1,
            "هلو" to 1, "نارگیل" to 1, "ملون" to 1, "پاپایا" to 1, "آلبالو" to 1,
            // Stage 2
            "سنجاب" to 2, "کرگدن" to 2, "طوطی برزیلی" to 2, "اسب آبی" to 2, "خرگوش" to 2,
            "گوسفند" to 2, "گورخر" to 2, "راسو" to 2, "روباه" to 2, "کروکودیل" to 2,
            // Stage 3
            "توت فرنگی" to 3, "گریپ فروت" to 3, "فلامینگو" to 3, "زیتون" to 3, "پاندا" to 3,
            "کانگورو" to 3, "گلابی" to 3, "سیب زمینی" to 3, "کاهو پیچ" to 3, "لاک پشت" to 3,
            // Stage 4
            "هات داگ" to 4, "مرغ کنتاکی" to 4, "بوریتو" to 4, "فسنجون" to 4, "همبرگر" to 4,
            "قورمه سبزی" to 4, "شیشلیک" to 4, "چلوکباب" to 4, "ته چین مرغ" to 4, "کوفته تبریزی" to 4
        )

        for ((word, stage) in words) {
            db.execSQL("INSERT INTO $TABLE_WORDS VALUES ('$word', $stage)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORDS")
        onCreate(db)
    }

    fun resetDatabase() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORDS")
        db.execSQL("DROP TABLE IF EXISTS scores_table")
        onCreate(db)
    }

    fun getAllWords(stage: Int): List<String> {
        val words = mutableListOf<String>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_WORDS WHERE $COLUMN_STAGE = ?", arrayOf(stage.toString()))
        if (cursor.moveToFirst()) {
            do {
                words.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return words
    }

    fun makingLists(level: Int, stage: Int): String {
        val words = getAllWords(stage)
        return words[level]
    }

    fun getScore(): Int {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT score FROM scores_table WHERE id = 1", null)
        return if (cursor != null && cursor.moveToFirst()) {
            val score = cursor.getInt(0)
            cursor.close()
            score
        } else {
            0
        }
    }

    fun saveScore(score: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("score", score)
        }
        db.update("scores_table", values, "id = ?", arrayOf("1"))
    }
}