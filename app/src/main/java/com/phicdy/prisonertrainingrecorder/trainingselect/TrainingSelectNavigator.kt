package com.phicdy.prisonertrainingrecorder.trainingselect

import android.view.View
import com.phicdy.prisonertrainingrecorder.data.Training

interface TrainingSelectNavigator {
    fun onTrainingClicked(view: View, training: Training)
}