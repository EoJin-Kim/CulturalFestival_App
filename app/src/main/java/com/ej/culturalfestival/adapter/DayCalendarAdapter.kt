package com.ej.culturalfestival.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.FestivalDetailDto

class DayCalendarAdapter(
    private val onClick : (String) -> Unit,
) : ListAdapter<FestivalDetailDto, DayCalendarAdapter.DayCalendarViewHolder>(DayCalendarDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_day_festival_item,parent,false)
        val holder = DayCalendarAdapter.DayCalendarViewHolder(view,onClick)
        return holder
    }

    override fun onBindViewHolder(holder: DayCalendarAdapter.DayCalendarViewHolder, position: Int) {
        val festivalDetailInfo = getItem(position)
        holder.bind(festivalDetailInfo)
    }

    class DayCalendarViewHolder (
        itemView : View,
        private val onClick : (String) -> Unit,
    ): RecyclerView.ViewHolder(itemView){

        private val dayTitleText : TextView = itemView.findViewById(R.id.day_festival_title)

        private val dayContentText : TextView = itemView.findViewById(R.id.day_festival_content)

        private val dayPhoneNumText : TextView = itemView.findViewById(R.id.day_festival_phone)

        private val dayAddress : TextView = itemView.findViewById(R.id.day_festival_address)
        private val dayAddressLayout : LinearLayout = itemView.findViewById(R.id.day_festival_address_layout)

        private val dayAuspcInsttText : TextView = itemView.findViewById(R.id.day_festival_auspc_instt)
        private val dayAuspcInsttLayout : LinearLayout = itemView.findViewById(R.id.day_auspc_instt_layout)

        private val daySuprtInsttText : TextView = itemView.findViewById(R.id.day_festival_suprt_instt)
        private val daySuprtInsttLayout : LinearLayout = itemView.findViewById(R.id.day_suprt_instt_layout)

        private val dayDateText : TextView = itemView.findViewById(R.id.day_festival_date)

        private val homePageButton : Button = itemView.findViewById(R.id.day_festival_hompage)

        lateinit var festivalDetailDto: FestivalDetailDto
        init {

        }

        fun bind(festivalDetailDto: FestivalDetailDto){

            this.festivalDetailDto = festivalDetailDto

            dayTitleText.text = festivalDetailDto.title
            dayContentText.text = festivalDetailDto.content
            dayPhoneNumText.text = festivalDetailDto.phone

            if (festivalDetailDto.lnmadr=="" && festivalDetailDto.rdnmadr==""){
                dayAddressLayout.visibility = View.GONE
            }
            else{
                if(festivalDetailDto.lnmadr!=""){
                    dayAddress.text = festivalDetailDto.lnmadr
                }
                else{
                    dayAddress.text = festivalDetailDto.rdnmadr
                }
            }

            if(festivalDetailDto.auspcInstt==""){
                dayAuspcInsttLayout.visibility = View.GONE
            }
            else{
                dayAuspcInsttText.text = festivalDetailDto.auspcInstt
            }

            if(festivalDetailDto.suprtInstt==""){
                daySuprtInsttLayout.visibility = View.GONE
            }
            else{
                daySuprtInsttText.text = festivalDetailDto.suprtInstt
            }

            dayDateText.text =festivalDetailDto.date

            if(festivalDetailDto.homepage!=""){
                homePageButton.setOnClickListener {
                    onClick(festivalDetailDto.homepage)
                }
            }
            else{
                homePageButton.visibility = View.GONE
            }
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