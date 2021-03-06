package com.example.dogexpertz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = arrayOf(Question::class), version = 3)
abstract class QuizDataBase : RoomDatabase() {

    abstract fun questionDao() : QuestionDao
    companion object {
        // we add volatile so that other threads gets the current changes and data
        @Volatile
        private var instance: QuizDataBase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            QuizDataBase::class.java,
            "QuestionsDB.db"
        ).build()
    }
}