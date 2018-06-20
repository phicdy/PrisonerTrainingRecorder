package com.phicdy.prisonertrainingrecorder.data.source

import android.arch.lifecycle.LiveData
import android.content.Context
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory
import com.phicdy.prisonertrainingrecorder.data.source.local.PrisonerTrainingDatabase
import com.phicdy.prisonertrainingrecorder.data.source.local.PrisonerTrainingLocalDataSource

class PrisonerTrainingRepository private constructor(val localDataSource: PrisonerTrainingDataSource) {

    companion object {
        fun getInstance(context: Context): PrisonerTrainingRepository {
            val db = PrisonerTrainingDatabase.getInstance(context)
            val localDataSource = PrisonerTrainingLocalDataSource(db.trainingHistoryDao())
            return PrisonerTrainingRepository(localDataSource)
        }
    }

    fun getAllHistories(): LiveData<List<TrainingHistory>> {
        return localDataSource.getAllHistories()
    }

    fun insertHistory(history: TrainingHistory) {
        localDataSource.insertHistory(history)
    }
}