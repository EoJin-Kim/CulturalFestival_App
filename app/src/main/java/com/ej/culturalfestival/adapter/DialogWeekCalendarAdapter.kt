package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.LocalDateDto
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.fragment.dialog.DialogMonthCalendarFragment
import java.time.LocalDate

class DialogWeekCalendarAdapter(
    private val onClick : (StartEndDate) -> Unit
) : ListAdapter<StartEndDate, DialogWeekCalendarAdapter.WeekCalendarViewHolder>(DialogWeekCalendarDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_week_calendar,parent,false)
        val holder = DialogWeekCalendarAdapter.WeekCalendarViewHolder(view,onClick)
        return holder
    }

    override fun onBindViewHolder(holder: WeekCalendarViewHolder, position: Int) {
    }

    class WeekCalendarViewHolder (itemView : View, private val onClick : (StartEndDate) -> Unit): RecyclerView.ViewHolder(itemView){

    }


}

object DialogWeekCalendarDiffCallback : DiffUtil.ItemCallback<StartEndDate>(){
    override fun areItemsTheSame(oldItem: StartEndDate, newItem: StartEndDate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StartEndDate, newItem: StartEndDate): Boolean {
        return oldItem == newItem
    }
}