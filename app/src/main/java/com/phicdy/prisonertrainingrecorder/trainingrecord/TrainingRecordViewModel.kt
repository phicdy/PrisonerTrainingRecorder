package com.phicdy.prisonertrainingrecorder.trainingrecord

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory
import com.phicdy.prisonertrainingrecorder.data.source.PrisonerTrainingRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class TrainingRecordViewModel : ViewModel() {
    private lateinit var navigator: TrainingRecordNavigator
    private lateinit var repository: PrisonerTrainingRepository
    private val trainingTitle: MutableLiveData<String> = MutableLiveData()
    val reps = ObservableField<String>()

    fun setTitle(title: String) {
        this.trainingTitle.value = title
    }

    fun setNavigator(navigator: TrainingRecordNavigator) {
        this.navigator = navigator
    }

    fun setRepository(repository: PrisonerTrainingRepository) {
        this.repository = repository
    }

    fun onRecordClicked() {
        launch(UI) {
            recordHistory()
            navigator.onRecordClicked()
        }
    }

    private suspend fun recordHistory() {
        withContext(CommonPool) {
            repository.insertHistory(
                    TrainingHistory(0, trainingTitle.value?:"", reps.get()?.toInt()?:0))
        }
    }
}