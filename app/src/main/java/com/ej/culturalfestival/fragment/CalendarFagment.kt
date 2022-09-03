package com.ej.culturalfestival.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.ViewPagerAdapter
import com.ej.culturalfestival.databinding.FragmentCalendarBinding
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CalendarFagment : Fragment() {
    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
    lateinit var calendarFagmentBinding : FragmentCalendarBinding
    lateinit var viewPager : ViewPager2

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

        viewPager = calendarFagmentBinding.viewpager2
        val tabLayout = calendarFagmentBinding.tabLayout

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { viewPager.setCurrentItem(it,false) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        val monthDayClickFun : (LocalDate) -> Unit = { date -> monthDayClick(date)}
        viewPager.adapter = ViewPagerAdapter(act.supportFragmentManager,lifecycle,monthDayClickFun)
        viewPager.isUserInputEnabled =false

//        viewPager.currentItem = 1


        TabLayoutMediator(tabLayout,viewPager){ tab,position ->
            tab.text = tabTitleArray[position]
        }.attach()



        return calendarFagmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        act.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun monthDayClick(date: LocalDate) {
        festivalViewModel.setDayFragmentDate(date)
        viewPager.currentItem = 0


    }


    companion object{
        fun newInstance() : CalendarFagment {
            return CalendarFagment()
        }
    }


}