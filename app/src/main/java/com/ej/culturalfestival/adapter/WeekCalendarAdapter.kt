package com.ej.culturalfestival.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.FestivalWeekInfoDto
import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class WeekCalendarAdapter(
   private val context: Context,
   private val onClick : (Long) -> Unit,
) : ListAdapter<FestivalWeekInfoDto, WeekCalendarAdapter.WeekCalendarViewHolder>(WeekCalendarDiffCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_week_item,parent,false)

        val holder = WeekCalendarViewHolder(view, context,onClick);
        return holder
    }

    override fun onBindViewHolder(holder: WeekCalendarViewHolder, position: Int) {
        val festivalWeekInfo = getItem(position)
        holder.bind(festivalWeekInfo)

    }

    class WeekCalendarViewHolder(itemView : View, private val context: Context,private val onClick : (Long) -> Unit): RecyclerView.ViewHolder(itemView){
        private val dayText : TextView = itemView.findViewById(R.id.week_item_day)
        private val dowButton : Button = itemView.findViewById(R.id.week_item_dow)
        private val openCloseText : TextView = itemView.findViewById(R.id.week_item_open_close)
        private val festivalCountText : TextView = itemView.findViewById(R.id.week_item_count)
        private val weekRecycler : RecyclerView = itemView.findViewById(R.id.week_item_recycler)

        private val closeTranslate = AnimationUtils.loadAnimation(context,R.anim.festival_close)
        private val openTranslate = AnimationUtils.loadAnimation(context,R.anim.festival_close)
        private var openCheck = true
        private val dayFormat = DecimalFormat("00")

        init {
            openCloseText.setOnClickListener {
                if (openCheck) {
//                    weekRecycler.startAnimation(closeTranslate)
                    weekRecycler.visibility = View.GONE
                    openCloseText.text= "열기"
                }
                else {
//                    weekRecycler.startAnimation(openTranslate)
                    weekRecycler.visibility = View.VISIBLE
                    openCloseText.text= "닫기"
                }
                openCheck = !openCheck

            }
        }



        fun bind(festivalWeekInfoDto: FestivalWeekInfoDto){
            val nowLocalDate = festivalWeekInfoDto.date
            val dayOfMonth = nowLocalDate.dayOfMonth
            val dayOfWeek = nowLocalDate.dayOfWeek

            dayText.text = "${dayFormat.format(dayOfMonth)}"
            dowButton.text = "${dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREA)}"
            festivalCountText.text = "축제 수 : ${festivalWeekInfoDto.festivalSummaryDtoList.size}"

            val adapter = WeekItemAdapter(onClick)

            adapter.submitList(festivalWeekInfoDto.festivalSummaryDtoList)

            val manager : RecyclerView.LayoutManager = LinearLayoutManager(context)

            weekRecycler.adapter = adapter
            weekRecycler.layoutManager = manager
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