package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentDayBinding
import com.ej.culturalfestival.databinding.FragmentMonthBinding
import com.ej.culturalfestival.util.CalendarUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MonthFragment : Fragment() {

    lateinit var monthYearText: TextView

    lateinit var monthFragmentBinding : FragmentMonthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        monthFragmentBinding = FragmentMonthBinding.inflate(inflater,container,false)

        // 초기화
        monthYearText = monthFragmentBinding.monthYearText
        val preBtn : Button = monthFragmentBinding.preBtn
        val nextBtn : Button = monthFragmentBinding.nextBtn

        // 현재 날짜
        CalendarUtil.selectedDate = LocalDate.now()

        // 화면 설정
        setMonthView()

        return monthFragmentBinding.root
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)
    }

    private fun monthYearFromDate(date : LocalDate):String{
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MM월 yyyy")
        return date.format(formatter)
    }

    companion object{
        fun newInstance() : MonthFragment {
            return MonthFragment()
        }
    }
}