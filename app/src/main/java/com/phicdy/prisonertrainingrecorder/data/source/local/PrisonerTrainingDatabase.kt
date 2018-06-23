package com.phicdy.prisonertrainingrecorder.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory

@Database(entities = [(TrainingHistory::class)], version = 1)
@TypeConverters(PrisonerTrainingConverter::class)
abstract class PrisonerTrainingDatabase: RoomDatabase() {
    companion object {
        fun getInstance(context: Context): PrisonerTrainingDatabase {
            return Room.databaseBuilder(context.applicationContext,
                    PrisonerTrainingDatabase::class.java, "PrisonerTraining.db")
                    .build()
        }
    }

    abstract fun trainingHistoryDao(): TrainingHistoryDao
}