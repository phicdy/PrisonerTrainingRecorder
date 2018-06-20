package com.phicdy.prisonertrainingrecorder.data.source.local

import android.arch.lifecycle.LiveData
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory
import com.phicdy.prisonertrainingrecorder.data.source.PrisonerTrainingDataSource

class PrisonerTrainingLocalDataSource(val dao: TrainingHistoryDao): PrisonerTrainingDataSource {
    override fun getAllHistories(): LiveData<List<TrainingHistory>> {
        return dao.getAll()
    }

    override fun insertHistory(history: TrainingHistory) {
        dao.insert(history)
    }
}