package com.phicdy.prisonertrainingrecorder

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.phicdy.prisonertrainingrecorder.data.Training
import com.phicdy.prisonertrainingrecorder.traininghistory.TrainingHistoryFragment
import com.phicdy.prisonertrainingrecorder.trainingrecord.TrainingRecordFragment
import com.phicdy.prisonertrainingrecorder.trainingrecord.TrainingRecordNavigator
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectFragment
import com.phicdy.prisonertrainingrecorder.trainingselect.TrainingSelectNavigator
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import com.phicdy.prisonertrainingrecorder.presentation.BottomNavigationBehavior


class MainActivity : AppCompatActivity(), TrainingSelectNavigator, TrainingRecordNavigator {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (item.itemId == navigation.selectedItemId) return@OnNavigationItemSelectedListener false
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
        changeScrollBehavior(false)
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
        changeScrollBehavior(true)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content, TrainingHistoryFragment.newInstance())
                .commit()
    }

    private fun changeScrollBehavior(enabled: Boolean) {
        val params = navigation.layoutParams as CoordinatorLayout.LayoutParams
        if (enabled) {
            params.behavior = BottomNavigationBehavior<View>(this, null)
        } else {
            params.behavior = null
        }
        navigation.requestLayout()
    }

    override fun closeKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showRecordSuccessSnackbar() {
        showSnackbarAboveBottomNavigationBar(R.string.record_result_success)
    }

    override fun showRecordNoRepsErrorSnackbar() {
        showSnackbarAboveBottomNavigationBar(R.string.record_result_no_reps)
    }

    override fun showRecord0RepsErrorSnackbar() {
        showSnackbarAboveBottomNavigationBar(R.string.record_result_0_reps)
    }

    override fun showRecordNoStepErrorSnackbar() {
        showSnackbarAboveBottomNavigationBar(R.string.record_result_no_step)
    }

    override fun showRecordNot1to10ErrorSnackbar() {
        showSnackbarAboveBottomNavigationBar(R.string.record_result_not_1_to_10_reps)
    }

    private fun showSnackbarAboveBottomNavigationBar(@StringRes res: Int) {
        Snackbar.make(fl_content, res, Snackbar.LENGTH_SHORT).show()
    }
}
