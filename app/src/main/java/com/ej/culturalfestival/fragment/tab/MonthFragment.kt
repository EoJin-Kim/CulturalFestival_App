package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.MonthCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentMonthBinding
import com.ej.culturalfestival.dto.FestivalDayInfoDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.fragment.dialog.MonthCalendarFragmentDialog
import com.ej.culturalfestival.util.CalendarUtil.Companion.formatter
import com.ej.culturalfestival.util.CalendarUtil.Companion.yearMonthFromDate
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.YearMonth


class MonthFragment(
    private val onClick : (LocalDate) -> Unit
) : Fragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by activityViewModels()

    lateinit var monthYearText: TextView
    lateinit var recycelrView : RecyclerView

    lateinit var binding : FragmentMonthBinding
    lateinit var monthCalendarFragmentDialog : MonthCalendarFragmentDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        monthFragmentBinding = FragmentMonthBinding.inflate(inflater,container,false)
        binding = FragmentMonthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 초기화
        monthYearText = binding.monthYearText
        recycelrView = binding.monthRecycler
        val preBtn : Button = binding.preMonth
        val nextBtn : Button = binding.nextMonth

        festivalViewModel.setMonthFragmentDate(LocalDate.now())
        monthYearText.text = yearMonthFromDate(LocalDate.now())

        val dialogMonthFun : (Int) -> Unit = { month -> dialogMonthClick(month)}
        monthCalendarFragmentDialog = MonthCalendarFragmentDialog.newInstance(
            dialogMonthFun
        )
        monthYearText.setOnClickListener {
            monthCalendarFragmentDialog.show(act.supportFragmentManager, "월 dialog")
        }
        preBtn.setOnClickListener {

            val nowDate = festivalViewModel.monthFragmentDate.value
            val preDate = nowDate!!.minusMonths(1)
            monthYearText.text = yearMonthFromDate(preDate)

            festivalViewModel.setMonthFragmentDate(preDate)

            val festivalList =getFestivalList(preDate)
            festivalList.observe(viewLifecycleOwner){
                setMonthView(it)
            }
        }


        nextBtn.setOnClickListener {
            val nowDate = festivalViewModel.monthFragmentDate.value
            val nextDate = nowDate!!.plusMonths(1)
            monthYearText.text = yearMonthFromDate(nextDate)

            festivalViewModel.setMonthFragmentDate(nextDate)

            val festivalList =getFestivalList(nextDate)
            festivalList.observe(viewLifecycleOwner){
                setMonthView(it)
            }
        }
        // 현재 날짜


        val festivalList = getFestivalList(festivalViewModel.monthFragmentDate.value!!)
        festivalList.observe(viewLifecycleOwner){
            setMonthView(it)
        }

    }

    private fun dialogMonthClick(month: Int) {
        Log.d("click","click")
    }

    private fun getFestivalList(date: LocalDate): LiveData<MutableList<FestivalDto>> {
        val firstLocalDate = LocalDate.of(date.year, date.month, 1)
        val lastDay: Int = date.lengthOfMonth()
        val lastLocalDate = LocalDate.of(date.year, date.month, lastDay)
        val festivalList = festivalViewModel.getFestival(firstLocalDate, lastLocalDate);
        return festivalList
    }

    private fun setMonthView(festivalList : List<FestivalDto>) {
        val nowDate = festivalViewModel.monthFragmentDate.value!!
        // 년월 텍스트뷰 셋팅


        // 해당 월 날짜 가져오기
        val dayList = daysInMonthArray(nowDate,festivalList)


        val funOpenHompage : (LocalDate) -> Unit = {date -> calendarDayClick(date)}
        // 어뎁터 데이터 적용
        val adapter = MonthCalendarAdapter(funOpenHompage)
        adapter.submitList(dayList)

        // 레이아웃 설정 (열 7개)
        val manager : RecyclerView.LayoutManager = GridLayoutManager(requireContext(),7)


        // 레이아웃 적용
        recycelrView.layoutManager = manager

        //어뎁터 적용
        recycelrView.adapter = adapter

    }

    private fun daysInMonthArray(date : LocalDate, festivalList: List<FestivalDto>) : MutableList<FestivalDayInfoDto?>{
        val dayList :MutableList<FestivalDayInfoDto?> = mutableListOf()
        val yearMonth = YearMonth.from(date)

        // 해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        val lastDay : Int = yearMonth.lengthOfMonth()

        // 해당 월의 첫 번째 날 가져오기 (예 4월1일)
        val firstDay : LocalDate = date.withDayOfMonth(1)

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

        var festivalIdx = 0
        var nowDay = 1
        for (i in 1 until (calendarSize+1)) {
            if (i <= dayOfWeek ) {
                dayList.add(null)
            }
            else if( i > lastDay + dayOfWeek){
                dayList.add(null)
            }
            else{
                var count = 0
                for (idx in festivalIdx until festivalList.size){
                    val festivalDto = festivalList[idx]
                    val festivalStartLocalDate = LocalDate.parse(festivalDto.fstvlStartDate, formatter);
                    val festivalEndLocalDate = LocalDate.parse(festivalDto.fstvlEndDate, formatter);

                    val nowDayLocalDate = LocalDate.of(date.year,date.month,nowDay)

                    if(
                        (festivalStartLocalDate.isBefore(nowDayLocalDate) || festivalStartLocalDate.isEqual(nowDayLocalDate)) &&
                        (festivalEndLocalDate.isAfter(nowDayLocalDate) || festivalEndLocalDate.isEqual(nowDayLocalDate))
                    ){
                        count++
                    }
                }
                val festivalDayInfoDto = FestivalDayInfoDto(LocalDate.of(date.year,date.month,i-dayOfWeek),count)
                dayList.add(festivalDayInfoDto)
                nowDay++
            }
        }

        return dayList
    }
    private fun calendarDayClick(date : LocalDate){
        Log.d("click","$date")
        onClick(date)
    }


    companion object{
        fun newInstance(onClick : (LocalDate) -> Unit) : MonthFragment {
            return MonthFragment(onClick)
        }
    }
}