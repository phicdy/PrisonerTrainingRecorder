package com.phicdy.prisonertrainingrecorder.trainingselect

import android.arch.lifecycle.ViewModel
import com.phicdy.prisonertrainingrecorder.data.Training

class TrainingSelectViewModel: ViewModel() {
    private var navigator: TrainingSelectNavigator? = null

    fun setNavigator(navigator: TrainingSelectNavigator) {
        this.navigator = navigator
    }

    fun onTrainingClicked(training: Training) {
        if (navigator == null) return
        navigator!!.onTrainingClicked(training)
    }
}