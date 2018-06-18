package com.phicdy.prisonertrainingrecorder.trainingselect

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
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.phicdy.prisonertrainingrecorder.R
import com.phicdy.prisonertrainingrecorder.data.Training

class TrainingRecordFragment : Fragment() {

    private var mListener: TrainingSelectNavigator? = null

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
        val view = inflater.inflate(R.layout.fragment_training_record, container, false)
        val title = view.findViewById<TextView>(R.id.tv_training_record_title)
        title.text = arguments?.getString(ARG_TRAINING_TITLE) ?: ""
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TrainingSelectNavigator) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement TrainingSelectNavigator")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = view.findViewById<TextView>(R.id.tv_training_record_title)
        ViewCompat.setTransitionName(title, TRAINING_TRANSITION_NAME)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}
