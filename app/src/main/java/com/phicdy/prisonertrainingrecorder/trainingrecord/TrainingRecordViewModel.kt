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
import java.lang.NumberFormatException

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
        val repsValue = reps.get()?: ""
        val repsInt: Int
        try {
            repsInt = repsValue.toInt()
        } catch (e: NumberFormatException) {
            navigator.showRecordNoRepsErrorSnackbar()
            return
        }
        if (repsInt <= 0) {
            navigator.showRecord0RepsErrorSnackbar()
            return
        }
        launch(UI) {
            recordHistory(trainingTitle.value.toString(), repsInt)
            navigator.closeKeyboard()
            navigator.showRecordSuccessSnackbar()
            reps.set("")
        }
    }

    private suspend fun recordHistory(title: String, reps: Int) {
        withContext(CommonPool) {
            repository.insertHistory(TrainingHistory(0, title, reps))
        }
    }
}