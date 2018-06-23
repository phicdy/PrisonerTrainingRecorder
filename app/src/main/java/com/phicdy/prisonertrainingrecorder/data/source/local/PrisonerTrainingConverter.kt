package com.phicdy.prisonertrainingrecorder.data.source.local

import android.arch.persistence.room.TypeConverter
import java.util.Date


class PrisonerTrainingConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}