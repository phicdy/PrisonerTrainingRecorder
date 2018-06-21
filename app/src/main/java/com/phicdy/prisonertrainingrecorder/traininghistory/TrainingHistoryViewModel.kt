package com.phicdy.prisonertrainingrecorder.traininghistory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory
import com.phicdy.prisonertrainingrecorder.data.source.PrisonerTrainingRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

class TrainingHistoryViewModel: ViewModel() {
    private lateinit var repository: PrisonerTrainingRepository

    val historyList: LiveData<List<TrainingHistory>> by lazy {
        runBlocking {
            val job = async(CommonPool) {
                return@async repository.getAllHistories()
            }
            return@runBlocking job.await()
        }
    }

    fun setRepository(repository: PrisonerTrainingRepository) {
        this.repository = repository
    }
}