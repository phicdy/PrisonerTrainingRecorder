package com.phicdy.prisonertrainingrecorder

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.phicdy.prisonertrainingrecorder.data.Training
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectNavigator

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

    private fun showTraining() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content, TrainingSelectFragment())
                .commit()
    }

    override fun onTrainingClicked(training: Training) {
        Snackbar.make(navigation, training.title, Snackbar.LENGTH_SHORT).show()
    }
}
