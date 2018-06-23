package com.phicdy.prisonertrainingrecorder.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity
data class TrainingHistory constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "training_name")
    var trainingName: String,

    @ColumnInfo(name = "reps")
    var reps: Int,

    @ColumnInfo(name = "date")
    var date: Date
)