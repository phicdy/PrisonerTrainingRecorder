package com.phicdy.prisonertrainingrecorder.trainingrecord

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.transition.ChangeBounds
import android.support.transition.ChangeTransform
import android.support.transition.Slide
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.phicdy.prisonertrainingrecorder.R
import com.phicdy.prisonertrainingrecorder.data.Training
import com.phicdy.prisonertrainingrecorder.databinding.TrainingRecordFragmentBinding

class TrainingRecordFragment : Fragment() {

    private lateinit var binding: TrainingRecordFragmentBinding
    private val trainingRecordViewModel: TrainingRecordViewModel by lazy {
        ViewModelProviders.of(this).get(TrainingRecordViewModel::class.java)
    }

    companion object {
        const val ENTER_TRANSITION_DURATION = 225L
        const val RETURN_TRANSITION_DURATION = 150L
        const val TRAINING_TRANSITION_NAME = "trainingTransitionName"
        private const val ARG_TRAINING_TITLE = "argTrainingTitle"

        fun newInstance(training: Training) = TrainingRecordFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TRAINING_TITLE, training.title)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = TransitionSet().apply {
            addTransition(Slide(Gravity.BOTTOM).apply {
                duration = ENTER_TRANSITION_DURATION
                interpolator = LinearOutSlowInInterpolator()
            })
        }
        returnTransition = TransitionSet().apply {
            addTransition(Slide(Gravity.BOTTOM).apply {
                duration = RETURN_TRANSITION_DURATION
                interpolator = FastOutLinearInInterpolator()
            })
        }
        sharedElementEnterTransition = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            duration = ENTER_TRANSITION_DURATION
            interpolator = FastOutSlowInInterpolator()
            addTransition(ChangeTransform())
            addTransition(ChangeBounds())
        }
        sharedElementReturnTransition = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            duration = RETURN_TRANSITION_DURATION
            interpolator = FastOutSlowInInterpolator()
            addTransition(ChangeTransform())
            addTransition(ChangeBounds())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = TrainingRecordFragmentBinding.inflate(inflater, container, false)
        binding.tvTrainingRecordTitle.text = arguments?.getString(ARG_TRAINING_TITLE) ?: ""
        binding.etReps.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                trainingRecordViewModel.setReps(try {
                    s.toString().toInt()
                } catch (e: NumberFormatException) {
                    0
                })
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TrainingRecordNavigator) {
            trainingRecordViewModel.setNavigator(context)
        } else {
            throw RuntimeException(context!!.toString() + " must implement TrainingRecordNavigator")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = view.findViewById<TextView>(R.id.tv_training_record_title)
        ViewCompat.setTransitionName(title, TRAINING_TRANSITION_NAME)
    }
}
