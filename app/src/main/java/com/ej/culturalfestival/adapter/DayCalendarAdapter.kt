package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.FestivalDetailDto

class DayCalendarAdapter : ListAdapter<FestivalDetailDto, DayCalendarAdapter.DayCalendarViewHolder>(DayCalendarDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_day_festival_item,parent,false)
        val holder = DayCalendarAdapter.DayCalendarViewHolder(view)
        return holder

    }

    override fun onBindViewHolder(holder: DayCalendarAdapter.DayCalendarViewHolder, position: Int) {
        val festivalDetailInfo = getItem(position)
        holder.bind(festivalDetailInfo)
    }

    class DayCalendarViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){

        private val dayText : TextView = itemView.findViewById(R.id.day_festival_title)

        lateinit var festivalDetailDto: FestivalDetailDto
        init {

        }

        fun bind(festivalDetailDto: FestivalDetailDto){

            this.festivalDetailDto = festivalDetailDto

            dayText.text = festivalDetailDto.title
        }
    }
}

object DayCalendarDiffCallback : DiffUtil.ItemCallback<FestivalDetailDto>(){
    override fun areItemsTheSame(oldItem: FestivalDetailDto, newItem: FestivalDetailDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FestivalDetailDto, newItem: FestivalDetailDto): Boolean {
        return oldItem == newItem
    }
}