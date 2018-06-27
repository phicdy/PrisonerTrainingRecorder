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
import java.util.Date


class TrainingRecordViewModel : ViewModel() {
    private lateinit var navigator: TrainingRecordNavigator
    private lateinit var repository: PrisonerTrainingRepository
    private val trainingTitle: MutableLiveData<String> = MutableLiveData()
    val reps = ObservableField<String>()
    val steps = ObservableField<String>()

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
        val (resultReps, repsInt) = validateReps()
        if (!resultReps) return
        val (resultStep, stepInt) = validateStep()
        if (!resultStep) return
        launch(UI) {
            recordHistory(trainingTitle.value.toString(), repsInt, stepInt)
            navigator.closeKeyboard()
            navigator.showRecordSuccessSnackbar()
            reps.set("")
        }
    }

    private fun validateReps(): Pair<Boolean, Int> {
        val repsInt = reps.get()?.toIntOrNull()
        if (repsInt == null) {
            navigator.showRecordNoRepsErrorSnackbar()
            return Pair(false, -1)
        }
        if (repsInt <= 0) {
            navigator.showRecord0RepsErrorSnackbar()
            return Pair(false, -1)
        }
        return Pair(true, repsInt)
    }

    private fun validateStep(): Pair<Boolean, Int> {
        val stepInt = steps.get()?.toIntOrNull()
        if (stepInt == null) {
            navigator.showRecordNoStepErrorSnackbar()
            return Pair(false, -1)
        }
        if (stepInt < 1 || stepInt > 10) {
            navigator.showRecordNot1to10ErrorSnackbar()
            return Pair(false, -1)
        }
        return Pair(true, stepInt)
    }

    private suspend fun recordHistory(title: String, reps: Int, step: Int) {
        withContext(CommonPool) {
            repository.insertHistory(TrainingHistory(0, title, reps, step, Date()))
        }
    }
}