package com.phicdy.prisonertrainingrecorder

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.phicdy.prisonertrainingrecorder.data.Training
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingRecordFragment
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectFragment
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectNavigator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TrainingSelectNavigator {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_training -> {
                showTraining()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showTraining()
    }

    override fun onTrainingClicked(view: View, training: Training) {
        showRecord(view, training)
    }

    private fun showTraining() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content, TrainingSelectFragment())
                .commit()
    }

    private fun showRecord(view: View, training: Training) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content, TrainingRecordFragment.newInstance(training))
                .addToBackStack(null)
                .addSharedElement(view, TrainingRecordFragment.TRAINING_TRANSITION_NAME)
                .commit()
    }

}
