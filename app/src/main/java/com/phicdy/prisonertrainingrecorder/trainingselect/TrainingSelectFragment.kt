package com.phicdy.prisonertrainingrecorder.trainingselect

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.transition.Explode
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.phicdy.prisonertrainingrecorder.R
import com.phicdy.prisonertrainingrecorder.data.Training
import com.phicdy.prisonertrainingrecorder.databinding.TrainingSelectFragmentBinding


class TrainingSelectFragment : Fragment() {

    companion object {
        const val EXIT_TRANSITION_DURATION = 150L
        const val REENTER_TRANSITION_DURATION = 225L
    }

    private lateinit var binding: TrainingSelectFragmentBinding
    private val trainingSelectViewModel: TrainingSelectViewModel by lazy {
        ViewModelProviders.of(this).get(TrainingSelectViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Explode().apply {
            mode = Explode.MODE_OUT
            duration = EXIT_TRANSITION_DURATION
            interpolator = FastOutLinearInInterpolator()
        }
        reenterTransition = Explode().apply {
            mode = Explode.MODE_IN
            duration = REENTER_TRANSITION_DURATION
            interpolator = LinearOutSlowInInterpolator()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = TrainingSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TrainingSelectNavigator) {
            trainingSelectViewModel.setNavigator(context)
        } else {
            throw RuntimeException(context.toString() + " must implement TrainingSelectNavigator")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvTraining.apply {
            adapter = TrainingAdapter(listOf(Training("Push Up"), Training("Squat"), Training("Pull Up"),
                    Training("Leg Raise"), Training("Bridge"), Training("Handstand Push Up")), trainingSelectViewModel)
        }
    }

    private class TrainingAdapter(val trainingList: List<Training>, val viewModel: TrainingSelectViewModel): RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_training_list, parent, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return trainingList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.text = trainingList[position].title
            holder.v.setOnClickListener {
                viewModel.onTrainingClicked(holder.title, trainingList[position])
            }
            ViewCompat.setTransitionName(holder.title, "title-$position")
        }

        class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
            var image: ImageView = v.findViewById<View>(R.id.iv_training_image) as ImageView
            var title: TextView = v.findViewById<View>(R.id.tv_training_title) as TextView
        }
    }
}
