package com.ej.culturalfestival.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ej.culturalfestival.fragment.tab.DayFragment
import com.ej.culturalfestival.fragment.tab.MonthFragment
import com.ej.culturalfestival.fragment.tab.WeekFragment
import java.time.LocalDate


class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    onClick : (LocalDate) -> Unit) : FragmentStateAdapter(fragmentManager,lifecycle) {
    private val dayFragment = DayFragment.newInstance()
    private val weekFragment = WeekFragment.newInstance()
    private val monthFragment = MonthFragment.newInstance(onClick)

    private val fragList = arrayListOf(
        dayFragment,
        weekFragment,
        monthFragment
    )


    override fun getItemCount(): Int {
        return fragList.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return dayFragment
            1 -> return weekFragment
            else -> return monthFragment
        }

    }


}