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
import com.ej.culturalfestival.fragment.dialog.DayCalendarFragmentDialog
import java.time.LocalDate

class DialogDayCalendarAdapter(
    private val onClick : (LocalDate) -> Unit,
    private val dayCalendarFragmentDialog : DayCalendarFragmentDialog
) : ListAdapter<LocalDateDto, DialogDayCalendarAdapter.DayCalendarViewHolder>(DialogDayCalendarDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: kotlin.Int): DayCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_calendar,parent,false)
        val holder = DialogDayCalendarAdapter.DayCalendarViewHolder(view,dayCalendarFragmentDialog, onClick)
        return holder
    }

    override fun onBindViewHolder(holder: DayCalendarViewHolder, position: kotlin.Int) {
        val date = getItem(position)
        holder.bind(date,position)
    }

    class DayCalendarViewHolder(itemView : View, private val dayCalendarFragmentDialog : DayCalendarFragmentDialog, private val onClick : (LocalDate) -> Unit,): RecyclerView.ViewHolder(itemView){

        private val parentView : View  = itemView.findViewById(R.id.parent_view)
        private val dayText  : TextView = itemView.findViewById(R.id.day_text)
        private val dayCountText  : TextView = itemView.findViewById(R.id.day_count)
        private val noDateColorCode = "#B5F3C7"

        fun bind(dateDto : LocalDateDto, position : Int){

            dayCountText.visibility = View.GONE
            if (dateDto.date == null) {

                parentView.setBackgroundColor(Color.parseColor(noDateColorCode))
                this.dayText.text = ""
            } else {
                this.dayText.text = "${dateDto.date.dayOfMonth}"

                parentView.setOnClickListener {
                    onClick(dateDto.date)
                    dayCalendarFragmentDialog.dismiss()
                }
            }

            // ?????????
            if ((position + 1) % 7 == 0) {
                this.dayText.setTextColor(Color.BLUE)
            }
            // ?????????
            else if (position == 0 || position % 7 == 0) {
                this.dayText.setTextColor(Color.RED)
            }
        }
    }
}

object DialogDayCalendarDiffCallback : DiffUtil.ItemCallback<LocalDateDto>(){
    override fun areItemsTheSame(oldItem: LocalDateDto, newItem: LocalDateDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LocalDateDto, newItem: LocalDateDto): Boolean {
        return oldItem == newItem
    }
}