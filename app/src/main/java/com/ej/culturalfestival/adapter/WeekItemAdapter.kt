package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.FestivalSummaryDto
import com.ej.culturalfestival.dto.FestivalWeekInfoDto

class WeekItemAdapter(
    private val onClick : (Long) -> Unit,
) : ListAdapter<FestivalSummaryDto, WeekItemAdapter.WeekItemViewHolder>(WeekItemDiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeekItemAdapter.WeekItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_week_festival_item,parent,false)

        val holder = WeekItemAdapter.WeekItemViewHolder(view,onClick);
        return holder
    }

    override fun onBindViewHolder(holder: WeekItemAdapter.WeekItemViewHolder, position: Int) {
        val festivalSummaryDto = getItem(position)
        holder.bind(festivalSummaryDto)
    }
    class WeekItemViewHolder (itemView : View,private val onClick : (Long) -> Unit,): RecyclerView.ViewHolder(itemView){
        private val festivalTitle : TextView = itemView.findViewById(R.id.week_festival_title)
        private val festivalContent : TextView = itemView.findViewById(R.id.week_festival_content)
        private val festivalDate : TextView = itemView.findViewById(R.id.week_festival_date)
        private var festivalId : Long = 0
        init {
            itemView.setOnClickListener {
                onClick(festivalId)
            }
        }
        fun bind(festivalSummaryDto: FestivalSummaryDto){
            festivalTitle.text = "${festivalSummaryDto.title}"
            festivalContent.text = "${festivalSummaryDto.content}"
            festivalDate.text = "${festivalSummaryDto.startDate} ~ ${festivalSummaryDto.endDate}"
            festivalId = festivalSummaryDto.id
        }
    }
}
object WeekItemDiffCallback : DiffUtil.ItemCallback<FestivalSummaryDto>(){
    override fun areItemsTheSame(oldItem: FestivalSummaryDto, newItem: FestivalSummaryDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FestivalSummaryDto, newItem: FestivalSummaryDto): Boolean {
        return oldItem == newItem
    }
}