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
import com.ej.culturalfestival.dto.LocalDateDto
import com.ej.culturalfestival.fragment.dialog.DialogDayCalendarFragment
import java.time.LocalDate

class DialogMonthCalendarAdapter(
    private val onClick : (LocalDate) -> Unit,
    private val dialogDayCalendarFragment : DialogDayCalendarFragment
) : ListAdapter<LocalDateDto, DialogMonthCalendarAdapter.MonthCalendarViewHolder>(DialogMonthCalendarDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_calendar,parent,false)
        val holder = DialogMonthCalendarAdapter.MonthCalendarViewHolder(view,dialogDayCalendarFragment, onClick)
        return holder
    }

    override fun onBindViewHolder(holder: MonthCalendarViewHolder, position: Int) {
        val date = getItem(position)
        holder.bind(date,position)
    }

    class MonthCalendarViewHolder(itemView : View, private val dialogDayCalendarFragment : DialogDayCalendarFragment, private val onClick : (LocalDate) -> Unit,): RecyclerView.ViewHolder(itemView){

        private val parentView : View  = itemView.findViewById(R.id.parent_view)
        private val dayText  : TextView = itemView.findViewById(R.id.day_text)
        private val dayCountText  : TextView = itemView.findViewById(R.id.day_count)

        private val noDateColorCode = "#B5F3C7"

        fun bind(dateDto : LocalDateDto,position : Int){



            dayCountText.visibility = View.GONE
            if (dateDto.date == null) {

                parentView.setBackgroundColor(Color.parseColor(noDateColorCode))
                this.dayText.text = ""
            } else {
                this.dayText.text = "${dateDto.date.dayOfMonth}"

                parentView.setOnClickListener {
                    onClick(dateDto.date)
                    dialogDayCalendarFragment.dismiss()
                }

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

object DialogMonthCalendarDiffCallback : DiffUtil.ItemCallback<LocalDateDto>(){
    override fun areItemsTheSame(oldItem: LocalDateDto, newItem: LocalDateDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LocalDateDto, newItem: LocalDateDto): Boolean {
        return oldItem == newItem
    }
}