package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.FestivalWeekInfoDto

class WeekCalendarAdapter(

) : ListAdapter<FestivalWeekInfoDto, WeekCalendarAdapter.WeekCalendarViewHolder>(WeekCalendarDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_month_item,parent,false)

        val holder = WeekCalendarViewHolder(view);
        return holder
    }

    override fun onBindViewHolder(holder: WeekCalendarViewHolder, position: Int) {
        val festivalWeekInfo = getItem(position)
        holder.bind(festivalWeekInfo)

    }

    class WeekCalendarViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val dayText : TextView = itemView.findViewById(R.id.week_item_day)
        private val dowButton : Button = itemView.findViewById(R.id.week_item_dow)
        private val weekRecycler : RecyclerView = itemView.findViewById(R.id.week_item_recycler)
        fun bind(festivalWeekInfoDto: FestivalWeekInfoDto){

        }
    }
}

object WeekCalendarDiffCallback : DiffUtil.ItemCallback<FestivalWeekInfoDto>(){
    override fun areItemsTheSame(oldItem: FestivalWeekInfoDto, newItem: FestivalWeekInfoDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FestivalWeekInfoDto, newItem: FestivalWeekInfoDto): Boolean {
        return oldItem == newItem
    }
}