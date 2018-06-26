package com.phicdy.prisonertrainingrecorder.traininghistory

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory
import com.phicdy.prisonertrainingrecorder.data.source.PrisonerTrainingRepository
import com.phicdy.prisonertrainingrecorder.databinding.ItemHistoryListBinding
import com.phicdy.prisonertrainingrecorder.databinding.TrainingHistoryFragmentBinding


class TrainingHistoryFragment : Fragment() {

    companion object {
        fun newInstance(): TrainingHistoryFragment = TrainingHistoryFragment()
    }

    private lateinit var binding: TrainingHistoryFragmentBinding
    private val trainingHistoryViewModel: TrainingHistoryViewModel by lazy {
        ViewModelProviders.of(this).get(TrainingHistoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = TrainingHistoryFragmentBinding.inflate(inflater, container, false)
        binding.hasHistory = false
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repository = PrisonerTrainingRepository.getInstance(context)
        trainingHistoryViewModel.setRepository(repository)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        trainingHistoryViewModel.historyList.observe(this, Observer<List<TrainingHistory>> { list ->
            binding.rvHistory.apply {
                list?.let {
                    binding.hasHistory = it.isNotEmpty()
                    adapter = HistoryAdapter(it)
                }
            }
        })
    }

    private class HistoryAdapter(val historyList: List<TrainingHistory>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

        private lateinit var binding: ItemHistoryListBinding
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            binding = ItemHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding.root)
        }

        override fun getItemCount(): Int {
            return historyList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            binding.history = historyList[position]
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    }
}
