package com.phicdy.prisonertrainingrecorder.data.source

import android.arch.lifecycle.LiveData
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory

interface PrisonerTrainingDataSource {
    fun getAllHistories(): LiveData<List<TrainingHistory>>
    fun insertHistory(history: TrainingHistory)
}