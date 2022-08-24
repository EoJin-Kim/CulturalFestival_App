package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.WeekCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentWeekBinding
import com.ej.culturalfestival.dto.FestivalSummaryDto
import com.ej.culturalfestival.dto.FestivalWeekInfoDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.dto.response.WeekInfoDto
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.util.CalendarUtil.Companion.formatter
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class WeekFragment : Fragment() {



    lateinit var weekFragmentBinding : FragmentWeekBinding
    lateinit var nowWeek : WeekInfoDto;

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        weekFragmentBinding = FragmentWeekBinding.inflate(inflater,container,false)

        val preBtn : Button = weekFragmentBinding.preWeek
        val nextBtn : Button = weekFragmentBinding.nextWeek

        preBtn.setOnClickListener {
            movePreWeek()
        }

        nextBtn.setOnClickListener {
            moveNextWeek()
        }

        setDayWeek(CalendarUtil.selectedDate)

        setTitleText()

        val result = festivalViewModel.getFestival(nowWeek.startDate,nowWeek.endDate)
        result.observe(viewLifecycleOwner){
            setWeekView(it)
        }


        return weekFragmentBinding.root
    }

    private fun movePreWeek(){


        val startDateDOM = nowWeek.startDate.dayOfMonth

        // 첫주의 시작이 일요일이 아니라면
        if(startDateDOM-7<1){
            nowWeek.startDate = LocalDate.of(nowWeek.startDate.year,nowWeek.startDate.month,1)
        }
        else{
            nowWeek.startDate = nowWeek.startDate.minusDays(7)
        }
        nowWeek.endDate = nowWeek.endDate.minusDays(7)


        val yearMonth = YearMonth.from(nowWeek.startDate)
        val totalDay = yearMonth.lengthOfMonth()

        //첫 번째 날 요일 가져오기(월:1, 일:7)
        var dayOfWeek : Int = nowWeek.startDate.dayOfWeek.value

        if (dayOfWeek == 7) {
            dayOfWeek =0
        }

        if(startDateDOM<=dayOfWeek){
            nowWeek.weekRow=1;
        }
        else{
            var chkDate = (7 - dayOfWeek)
            for(row : Int in 2 until 6){

                if(chkDate==startDateDOM){
                    nowWeek.weekRow = row
                    setTitleText()
                    break
                }
                chkDate +=7

            }
        }




    }

    private fun moveNextWeek(){
        val day = nowWeek.endDate.dayOfMonth
    }


    private fun daysInWeekArray(weekInfo: WeekInfoDto,festivalList : MutableList<FestivalDto>) : MutableList<FestivalWeekInfoDto>{
        val festivalWeekInfoList : MutableList<FestivalWeekInfoDto> = mutableListOf()
        val startDay = weekInfo.startDate.dayOfMonth
        val endDay = weekInfo.endDate.dayOfMonth
        for (i:Int in startDay until endDay+1){
            val nowDayLocalDate = LocalDate.of(weekInfo.startDate.year, weekInfo.startDate.month, i)
            val festivalSummaryList : MutableList<FestivalSummaryDto> = mutableListOf()

            for (festivalDto in festivalList) {
                val festivalStartLocalDate = LocalDate.parse(festivalDto.fstvlStartDate, formatter);
                val festivalEndLocalDate = LocalDate.parse(festivalDto.fstvlEndDate, formatter);
                if(
                    (festivalStartLocalDate.isBefore(nowDayLocalDate) || festivalStartLocalDate.isEqual(nowDayLocalDate)) &&
                    (festivalEndLocalDate.isAfter(nowDayLocalDate) || festivalEndLocalDate.isEqual(nowDayLocalDate))
                ){
                    val festivalSummaryDto = FestivalSummaryDto(
                        festivalDto.id,
                        festivalDto.fstvlNm,
                        festivalDto.fstvlCo,
                        festivalDto.fstvlStartDate,
                        festivalDto.fstvlEndDate
                    )
                    festivalSummaryList.add(festivalSummaryDto)
                }
            }
            val festivalWeekInfoDto = FestivalWeekInfoDto(nowDayLocalDate,festivalSummaryList)
            festivalWeekInfoList.add(festivalWeekInfoDto)
        }
        return festivalWeekInfoList

    }
    private fun setWeekView(festivalList : MutableList<FestivalDto>){


        val dayList = daysInWeekArray(nowWeek,festivalList)

        val adapter = WeekCalendarAdapter(requireContext())
        adapter.submitList(dayList)

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        val weekRecycler = weekFragmentBinding.weekRecycler

        weekRecycler.adapter = adapter
        weekRecycler.layoutManager = manager

    }
    private fun setDayWeek(date :LocalDate){

        val yearMonth = YearMonth.from(date)

        // 해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        val monthDayCnt : Int = yearMonth.lengthOfMonth()

        // 해당 월의 첫 번째 날 가져오기 (예 4월1일)
        val firstDay : LocalDate = CalendarUtil.selectedDate.withDayOfMonth(1)

        //첫 번째 날 요일 가져오기(월:1, 일:7)
        var dayOfWeek : Int = firstDay.dayOfWeek.value


        if (dayOfWeek == 7) {
            dayOfWeek = 0
        }
        val firstWeekCnt = 7-dayOfWeek
        val lastWeekCnt = (monthDayCnt-firstWeekCnt)%7
        val fullWeekDayCount = monthDayCnt-firstWeekCnt-lastWeekCnt

        val nowDay = date.dayOfMonth

        if(nowDay <=firstWeekCnt){
            val startDate = LocalDate.of(date.year,date.month,1)
            val endDate = LocalDate.of(date.year,date.month,firstWeekCnt)
            nowWeek = WeekInfoDto(1,startDate,endDate)
            return
        }


        for ( weekRow : Int in 1 until fullWeekDayCount/2+1 ){
            if(nowDay<=firstWeekCnt+weekRow*7){
                val startDate = LocalDate.of(date.year,date.month,firstWeekCnt+(weekRow-1)*7 +1)
                val endDate = LocalDate.of(date.year,date.month,firstWeekCnt+weekRow*7)
                nowWeek = WeekInfoDto(weekRow+1,startDate,endDate)
                return
            }
        }

        val startDate = LocalDate.of(date.year,date.month,firstWeekCnt+fullWeekDayCount+1)
        val endDate = LocalDate.of(date.year,date.month,monthDayCnt)
        nowWeek = WeekInfoDto(fullWeekDayCount/2,startDate,endDate)


    }
    private fun calcMonthWeek(month : Int) : MutableList<WeekInfoDto>{
        val weekList : MutableList<WeekInfoDto> = mutableListOf()

        return weekList
    }

    private fun setTitleText(){
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MM")
        val monthStr = nowWeek.startDate.format(formatter)
        weekFragmentBinding.weekTitle.text = "${monthStr}월 ${nowWeek.weekRow}주"
    }

    companion object{
        fun newInstance() : WeekFragment {
            return WeekFragment()
        }
    }

}