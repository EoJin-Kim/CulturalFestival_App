package com.ej.culturalfestival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.fragment.dialog.MonthCalendarFragmentDialog

class DialogMonthCalendarAdapter(
    private val onClick : (Int) -> Unit,
    private val monthCalendarFragmentDialog : MonthCalendarFragmentDialog
): ListAdapter<Int, DialogMonthCalendarAdapter.MonthCalendarViewHolder>(DialogMonthCalendarDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: kotlin.Int
    ): MonthCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_month_calendar,parent,false)
        val holder = DialogMonthCalendarAdapter.MonthCalendarViewHolder(view,monthCalendarFragmentDialog, onClick)
        return holder
    }

    override fun onBindViewHolder(holder: MonthCalendarViewHolder, position: kotlin.Int) {
        val month = getItem(position)
        holder.bind(month,position)
    }

    class MonthCalendarViewHolder(itemView : View, private val monthCalendarFragmentDialog : MonthCalendarFragmentDialog, private val onClick : (Int) -> Unit,): RecyclerView.ViewHolder(itemView){
        val monthText : TextView = itemView.findViewById(R.id.dialog_month)

        fun bind(month: Int, position: Int) {
            monthText.text = "${month}월"

            monthText.setOnClickListener {
                onClick(month)
            }
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