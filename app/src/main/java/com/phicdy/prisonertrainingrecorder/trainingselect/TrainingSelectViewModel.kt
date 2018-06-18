package com.phicdy.prisonertrainingrecorder.trainingselect

import android.arch.lifecycle.ViewModel
import android.view.View
import com.phicdy.prisonertrainingrecorder.data.Training

class TrainingSelectViewModel: ViewModel() {
    private var navigator: TrainingSelectNavigator? = null

    fun setNavigator(navigator: TrainingSelectNavigator) {
        this.navigator = navigator
    }

    fun onTrainingClicked(view: View, training: Training) {
        if (navigator == null) return
        navigator!!.onTrainingClicked(view, training)
    }
}