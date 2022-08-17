package com.ej.culturalfestival.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import java.time.LocalDate

class CalendarAdapter(

) : ListAdapter<LocalDate,CalendarAdapter.CalendarViewHolder>(CalendarDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_calendar,parent,false)
        val holder = CalendarViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val localDate = getItem(position)
        holder.bind(localDate,position)
    }

    class CalendarViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val parentView : View  = itemView.findViewById(R.id.parent_view)
        private val dayText  : TextView = itemView.findViewById(R.id.day_text)
        init {

        }

        fun bind(day : LocalDate?,position: Int){
            if (day == null) {
                this.dayText.text = ""
            } else {
                this.dayText.text = "${day.dayOfMonth}"
            }

            // 토요일
            if ((position + 1) % 7 == 0) {
                this.dayText.setTextColor(Color.BLUE)
            }
            // 일요일
            else if (position == 0 || position % 7 == 0) {
                this.dayText.setTextColor(Color.RED)
            }

        }
    }
}

object CalendarDiffCallback : DiffUtil.ItemCallback<LocalDate>(){
    override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
        return oldItem == newItem
    }
}