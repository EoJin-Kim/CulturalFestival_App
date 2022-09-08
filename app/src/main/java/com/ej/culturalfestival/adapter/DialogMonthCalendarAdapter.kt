package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.dto.LocalDateDto
import com.ej.culturalfestival.fragment.dialog.DialogMonthCalendarFragment

class DialogMonthCalendarAdapter(
    private val onClick : (Int) -> Unit,
    private val dialogMonthCalendarFragment : DialogMonthCalendarFragment
): ListAdapter<Int, DialogMonthCalendarAdapter.MonthCalendarViewHolder>(DialogMonthCalendarDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: kotlin.Int
    ): MonthCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_calendar,parent,false)
        val holder = DialogMonthCalendarAdapter.MonthCalendarViewHolder(view,dialogMonthCalendarFragment, onClick)
        return holder
    }

    override fun onBindViewHolder(holder: MonthCalendarViewHolder, position: kotlin.Int) {
        val month = getItem(position)
        holder.bind(month,position)
    }

    class MonthCalendarViewHolder(itemView : View, private val dialogMonthCalendarFragment : DialogMonthCalendarFragment, private val onClick : (Int) -> Unit,): RecyclerView.ViewHolder(itemView){


        fun bind(month: Int, position: kotlin.Int) {

        }
    }
}

object DialogMonthCalendarDiffCallback : DiffUtil.ItemCallback<Int>(){
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}