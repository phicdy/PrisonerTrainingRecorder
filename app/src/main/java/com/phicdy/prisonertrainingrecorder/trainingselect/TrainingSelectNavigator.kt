package com.phicdy.prisonertrainingrecorder.trainingselect

import com.phicdy.prisonertrainingrecorder.data.Training

interface TrainingSelectNavigator {
    fun onTrainingClicked(training: Training)
}