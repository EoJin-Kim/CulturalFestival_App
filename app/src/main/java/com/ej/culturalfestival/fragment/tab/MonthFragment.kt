package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.adapter.CalendarAdapter
import com.ej.culturalfestival.databinding.FragmentMonthBinding
import com.ej.culturalfestival.util.CalendarUtil
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MonthFragment : Fragment() {

    lateinit var monthYearText: TextView
    lateinit var recycelrView : RecyclerView

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
        recycelrView = monthFragmentBinding.recyclerView
        val preBtn : Button = monthFragmentBinding.preBtn
        val nextBtn : Button = monthFragmentBinding.nextBtn

        preBtn.setOnClickListener {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1)
            setMonthView()
        }

        nextBtn.setOnClickListener {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1)
            setMonthView()
        }
        // 현재 날짜
        CalendarUtil.selectedDate = LocalDate.now()

        // 화면 설정
        setMonthView()

        return monthFragmentBinding.root
    }

    private fun setMonthView() {
        // 년월 텍스트뷰 셋팅
        monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)

        // 해당 월 날짜 가져오기
        val dayList = daysInMonthArray(CalendarUtil.selectedDate)

        // 어뎁터 데이터 적용
        val adapter = CalendarAdapter()
        adapter.submitList(dayList)

        // 레이아웃 설정 (열 7개)
        val manager : RecyclerView.LayoutManager = GridLayoutManager(requireContext(),7)


        // 레이아웃 적용
        recycelrView.layoutManager = manager

        //어뎁터 적용
        recycelrView.adapter = adapter

    }

    private fun daysInMonthArray(date : LocalDate) : MutableList<LocalDate?>{
        val dayList :MutableList<LocalDate?> = mutableListOf()
        val yearMonth = YearMonth.from(date)

        // 해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        val lastDay : Int = yearMonth.lengthOfMonth()

        // 해당 월의 첫 번째 날 가져오기 (예 4월1일)
        val firstDay : LocalDate = CalendarUtil.selectedDate.withDayOfMonth(1)

        //첫 번째 날 요일 가져오기(월:1, 일:7)
        var dayOfWeek : Int = firstDay.dayOfWeek.value


        var calendarSize = lastDay
        if (dayOfWeek != 7) {
            calendarSize += dayOfWeek
        }
        val nextMonthDay = if (calendarSize%7 ==0)  0 else (7-calendarSize%7)
        calendarSize+=nextMonthDay

        if (dayOfWeek == 7) {
            dayOfWeek =0
        }
        // 날짜 생성
        for (i in 1 until (calendarSize+1)) {
            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add(null);
            }
            else{
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.year,CalendarUtil.selectedDate.month,i-dayOfWeek))
            }
        }
        return dayList
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