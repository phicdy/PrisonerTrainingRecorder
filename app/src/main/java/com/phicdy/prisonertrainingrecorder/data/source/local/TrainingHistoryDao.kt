package com.phicdy.prisonertrainingrecorder.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.phicdy.prisonertrainingrecorder.data.TrainingHistory


@Dao
interface TrainingHistoryDao {
    @Query("SELECT * FROM traininghistory")
    fun getAll(): LiveData<List<TrainingHistory>>

    @Insert
    fun insert(trainingHistory: TrainingHistory)
}