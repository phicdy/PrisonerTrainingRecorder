package com.phicdy.prisonertrainingrecorder.trainingselect

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
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

    private lateinit var binding: TrainingSelectFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = TrainingSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvTraining.apply {
            adapter = TrainingAdapter(listOf(Training("Push Up"), Training("Squat"), Training("Pull Up"),
                    Training("Leg Raise"), Training("Bridge"), Training("Handstand Push Up")))
        }
    }

    private class TrainingAdapter(val trainingList: List<Training>): RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

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
                Snackbar.make(it, holder.title.text, Snackbar.LENGTH_SHORT).show()
            }
        }

        class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
            var image: ImageView = v.findViewById<View>(R.id.iv_training_image) as ImageView
            var title: TextView = v.findViewById<View>(R.id.tv_training_title) as TextView
        }
    }
}
