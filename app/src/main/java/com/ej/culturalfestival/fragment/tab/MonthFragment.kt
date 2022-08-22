package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.CalendarAdapter
import com.ej.culturalfestival.databinding.FragmentMonthBinding
import com.ej.culturalfestival.dto.FestivalDayInfo
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MonthFragment : Fragment() {



    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }

    lateinit var monthYearText: TextView
    lateinit var recycelrView : RecyclerView

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

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
        recycelrView = monthFragmentBinding.monthRecycler
        val preBtn : Button = monthFragmentBinding.preBtn
        val nextBtn : Button = monthFragmentBinding.nextBtn

        preBtn.setOnClickListener {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1)
            val preDate = CalendarUtil.selectedDate
            val festivalList =getFestivalList(preDate)
            festivalList.observe(viewLifecycleOwner){
                setMonthView(it)
            }
        }

        nextBtn.setOnClickListener {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1)
            val nextDate = CalendarUtil.selectedDate
            val festivalList =getFestivalList(nextDate)
            festivalList.observe(viewLifecycleOwner){
                setMonthView(it)
            }
        }
        // 현재 날짜

        CalendarUtil.selectedDate = LocalDate.now()
        val nowDate = CalendarUtil.selectedDate
        val festivalList = getFestivalList(nowDate)
        festivalList.observe(viewLifecycleOwner){
            setMonthView(it)
        }


        // 화면 설정


        return monthFragmentBinding.root
    }

    private fun getFestivalList(date: LocalDate): LiveData<MutableList<FestivalDto>> {
        val firstLocalDate = LocalDate.of(date.year, date.month, 1)
        val lastDay: Int = date.lengthOfMonth()
        val lastLocalDate = LocalDate.of(date.year, date.month, lastDay)
        val festivalList = festivalViewModel.getFestival(firstLocalDate, lastLocalDate);
        return festivalList
    }

    private fun setMonthView(festivalList : List<FestivalDto>) {
        // 년월 텍스트뷰 셋팅
        monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)

        // 해당 월 날짜 가져오기
        val dayList = daysInMonthArray(CalendarUtil.selectedDate,festivalList)

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

    private fun daysInMonthArray(date : LocalDate, festivalList: List<FestivalDto>) : MutableList<FestivalDayInfo?>{
        val dayList :MutableList<FestivalDayInfo?> = mutableListOf()
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
//        var preDayCount = dayOfWeek
//        var nextDayCount = 1

        var festivalIdx = 0
        var nowDay = 1
        for (i in 1 until (calendarSize+1)) {
            if (i <= dayOfWeek ) {
                dayList.add(null)
//                val preMonthDay  = CalendarUtil.selectedDate.minusMonths(1);
//                val preLocalDate = YearMonth.from(preMonthDay)
//                val preMonthLastDay = preLocalDate.lengthOfMonth()
//                dayList.add(LocalDate.of(preLocalDate.year,preLocalDate.month,preMonthLastDay-preDayCount-- +1))

            }
            else if( i >= lastDay + dayOfWeek){
                dayList.add(null)
//                val nextMonth  = CalendarUtil.selectedDate.plusMonths(1);
//                val nextLocalDate = YearMonth.from(nextMonth)
//                dayList.add(LocalDate.of(nextLocalDate.year,nextLocalDate.month,nextDayCount++))
            }
            else{
                var count = 0
                for (idx in festivalIdx until festivalList.size){
                    val festivalDto = festivalList[idx]
                    val festivalStartLocalDate = LocalDate.parse(festivalDto.fstvlStartDate, formatter);
                    val festivalEndLocalDate = LocalDate.parse(festivalDto.fstvlEndDate, formatter);

                    val nowDayLocalDate = LocalDate.of(CalendarUtil.selectedDate.year,CalendarUtil.selectedDate.month,nowDay)

                    if(
                        (festivalStartLocalDate.isBefore(nowDayLocalDate) || festivalStartLocalDate.isEqual(nowDayLocalDate)) &&
                        (festivalEndLocalDate.isAfter(nowDayLocalDate) || festivalEndLocalDate.isEqual(nowDayLocalDate))
                    ){
                        count++
                    }
                    else{
                        break
                    }
                }
                val festivalDayInfo = FestivalDayInfo(LocalDate.of(CalendarUtil.selectedDate.year,CalendarUtil.selectedDate.month,i-dayOfWeek),count)
                dayList.add(festivalDayInfo)
                nowDay++
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