package com.phicdy.prisonertrainingrecorder.trainingrecord

interface TrainingRecordNavigator {
    fun closeKeyboard()
    fun showRecordSuccessSnackbar()
    fun showRecordNoRepsErrorSnackbar()
    fun showRecord0RepsErrorSnackbar()
}