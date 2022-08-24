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
import com.ej.culturalfestival.dto.FestivalDayInfoDto

class MonthCalendarAdapter(

) : ListAdapter<FestivalDayInfoDto,MonthCalendarAdapter.MonthCalendarViewHolder>(MonthCalendarDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_calendar,parent,false)
        val holder = MonthCalendarViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: MonthCalendarViewHolder, position: Int) {
        val festivalDayInfo = getItem(position)
        holder.bind(festivalDayInfo,position)
    }

    class MonthCalendarViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val parentView : View  = itemView.findViewById(R.id.parent_view)
        private val dayText  : TextView = itemView.findViewById(R.id.day_text)
        private val countText : TextView = itemView.findViewById(R.id.day_count)

        private val noDateColorCode = "#B5F3C7"
        init {

        }

        fun bind(festivalDayInfoDto : FestivalDayInfoDto?, position: Int){
            if (festivalDayInfoDto == null) {

                parentView.setBackgroundColor(Color.parseColor(noDateColorCode))
                this.dayText.text = ""
                this.countText.text = ""
            } else {
                this.dayText.text = "${festivalDayInfoDto.date.dayOfMonth}"
                this.countText.text = "${festivalDayInfoDto.count}개"
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

object MonthCalendarDiffCallback : DiffUtil.ItemCallback<FestivalDayInfoDto>(){
    override fun areItemsTheSame(oldItem: FestivalDayInfoDto, newItem: FestivalDayInfoDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FestivalDayInfoDto, newItem: FestivalDayInfoDto): Boolean {
        return oldItem == newItem
    }
}