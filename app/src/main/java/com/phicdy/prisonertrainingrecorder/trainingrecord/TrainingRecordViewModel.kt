package com.phicdy.prisonertrainingrecorder.trainingrecord

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class TrainingRecordViewModel : ViewModel() {
    private var navigator: TrainingRecordNavigator? = null
    private val trainingTitle: MutableLiveData<String> = MutableLiveData()
    private val reps: MutableLiveData<Int> = MutableLiveData()

    fun setReps(reps: Int) {
        this.reps.value = reps
    }

    fun setTitle(title: String) {
        this.trainingTitle.value = title
    }

    fun setNavigator(navigator: TrainingRecordNavigator) {
        this.navigator = navigator
    }

    fun onRecordClicked() {
        if (navigator == null) return
        navigator!!.onRecordClicked()
    }
}