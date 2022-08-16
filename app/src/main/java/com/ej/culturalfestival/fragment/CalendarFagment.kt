package com.ej.culturalfestival.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.ViewPagerAdapter
import com.ej.culturalfestival.databinding.FragmentCalendarBinding
import com.ej.culturalfestival.util.CalendarUtil
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CalendarFagment : Fragment() {
    val act : MainActivity by lazy {activity as MainActivity}
    lateinit var calendarFagmentBinding : FragmentCalendarBinding

    private val tabTitleArray = arrayOf(
        "일별",
        "주별",
        "월별"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        calendarFagmentBinding = FragmentCalendarBinding.inflate(LayoutInflater.from(container!!.context),container,false)

        val viewPager = calendarFagmentBinding.viewpager2
        val tabLayout = calendarFagmentBinding.tabLayout

        viewPager.adapter = ViewPagerAdapter(act.supportFragmentManager,lifecycle)
        viewPager.isUserInputEnabled =false

        viewPager.currentItem = 2


        TabLayoutMediator(tabLayout,viewPager){ tab,position ->
            tab.text = tabTitleArray[position]
        }.attach()

        return calendarFagmentBinding.root
    }





    companion object{
        fun newInstance() : CalendarFagment {
            return CalendarFagment()
        }
    }


}