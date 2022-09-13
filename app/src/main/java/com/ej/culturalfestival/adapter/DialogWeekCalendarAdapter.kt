package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.fragment.dialog.DialogDayCalendarFragment
import com.ej.culturalfestival.fragment.dialog.DialogWeekCalendarFragment

class DialogWeekCalendarAdapter(
    private val onClick : (StartEndDate,Int) -> Unit,
    private val dialogWeekCalendarFragment : DialogWeekCalendarFragment
) : ListAdapter<StartEndDate, DialogWeekCalendarAdapter.WeekCalendarViewHolder>(DialogWeekCalendarDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_week_calendar,parent,false)
        val holder = DialogWeekCalendarAdapter.WeekCalendarViewHolder(view,dialogWeekCalendarFragment,onClick)
        return holder
    }

    override fun onBindViewHolder(holder: WeekCalendarViewHolder, position: Int) {
        val startEndDate = getItem(position)
        holder.bind(startEndDate,position)
    }

    class WeekCalendarViewHolder (
        itemView : View,
        private val dialogWeekCalendarFragment : DialogWeekCalendarFragment,
        private val onClick : (StartEndDate,Int) -> Unit,
    ): RecyclerView.ViewHolder(itemView){

        private val weekPositon : TextView = itemView.findViewById(R.id.week_position)
        lateinit var startEndDate : StartEndDate
        fun bind(startEndDate: StartEndDate, position: Int) {
            weekPositon.text = "${position+1} 주차"
            this.startEndDate = startEndDate

            weekPositon.setOnClickListener {
                onClick(startEndDate,position)
                dialogWeekCalendarFragment.dismiss()
            }
        }
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