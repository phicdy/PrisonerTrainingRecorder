package com.phicdy.prisonertrainingrecorder

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.phicdy.prisonertrainingrecorder.data.Training
import com.phicdy.prisonertrainingrecorder.traininghistory.TrainingHistoryFragment
import com.phicdy.prisonertrainingrecorder.trainingrecord.TrainingRecordFragment
import com.phicdy.prisonertrainingrecorder.trainingrecord.TrainingRecordNavigator
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectFragment
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectNavigator
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity(), TrainingSelectNavigator, TrainingRecordNavigator {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_training -> {
                showTraining()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                showHistory()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) showTraining()
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

    private fun showHistory() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content, TrainingHistoryFragment.newInstance())
                .commit()
    }

    override fun closeKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showRecordSuccessSnackbar() {
        Snackbar.make(navigation, R.string.record_result_success, Snackbar.LENGTH_SHORT).show()
    }

    override fun showRecordNoRepsErrorSnackbar() {
        Snackbar.make(navigation, R.string.record_result_no_reps, Snackbar.LENGTH_SHORT).show()
    }

    override fun showRecord0RepsErrorSnackbar() {
        Snackbar.make(navigation, R.string.record_result_0_reps, Snackbar.LENGTH_SHORT).show()
    }
}
