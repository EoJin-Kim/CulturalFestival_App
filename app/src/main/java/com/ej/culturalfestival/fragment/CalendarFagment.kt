package com.ej.culturalfestival.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.ViewPagerAdapter
import com.ej.culturalfestival.databinding.FragmentCalendarBinding
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate


class CalendarFagment : Fragment() {
    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
    lateinit var binding : FragmentCalendarBinding

    private val tabTitleArray = arrayOf(
        "일별",
        "주별",
        "월별"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val tabLayout = binding.tabLayout

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.viewpager2.setCurrentItem(it,false) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        val monthDayClickFun : (LocalDate) -> Unit = { date -> monthDayClick(date)}
        binding.viewpager2.apply {
            adapter = ViewPagerAdapter(act.supportFragmentManager,lifecycle,monthDayClickFun)
            isUserInputEnabled =false
        }
//        viewPager.currentItem = 1
        TabLayoutMediator(tabLayout,binding.viewpager2){ tab,position ->
            tab.text = tabTitleArray[position]
        }.attach()

    }

    override fun onResume() {
        super.onResume()
        act.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun monthDayClick(date: LocalDate) {
        festivalViewModel.setDayFragmentDate(date)
        binding.viewpager2.currentItem = 0
    }


    companion object{
        val TAG = "CalendarFagment"
        fun newInstance() : CalendarFagment {
            return CalendarFagment()
        }
    }


}