package com.phicdy.prisonertrainingrecorder.trainingrecord

import android.arch.lifecycle.ViewModel

class TrainingRecordViewModel: ViewModel() {
    private var navigator: TrainingRecordNavigator? = null

    fun setNavigator(navigator: TrainingRecordNavigator) {
        this.navigator = navigator
    }

    fun onRecordClicked() {
        if (navigator == null) return
        navigator!!.onRecordClicked()
    }
}