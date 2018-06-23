package com.phicdy.prisonertrainingrecorder.presentation.common.binding

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter(value = ["android:text"])
fun TextView.setDateText(date: Date?) {
    date ?: return
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:dd", Locale.getDefault())
    text = format.format(date)
}

