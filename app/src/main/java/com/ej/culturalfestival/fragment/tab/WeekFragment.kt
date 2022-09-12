package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.WeekCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentWeekBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.FestivalSummaryDto
import com.ej.culturalfestival.dto.FestivalWeekInfoDto
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.dto.WeekInfoDto
import com.ej.culturalfestival.fragment.dialog.DialogWeekCalendarFragment
import com.ej.culturalfestival.fragment.dialog.FestivalFragmentDialog
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.util.CalendarUtil.Companion.formatter
import com.ej.culturalfestival.util.CalendarUtil.Companion.moveNextWeek
import com.ej.culturalfestival.util.CalendarUtil.Companion.setDayWeek
import com.ej.culturalfestival.util.CalendarUtil.Companion.setWeekTitleText
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate


class WeekFragment : Fragment() {



    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }

    lateinit var weekFragmentBinding : FragmentWeekBinding
    lateinit var dialogWeekCalendarFragment : DialogWeekCalendarFragment
    lateinit var nowWeek : WeekInfoDto;


    lateinit var recycler : RecyclerView


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
        val weekText : TextView = weekFragmentBinding.weekTitle

        nowWeek = CalendarUtil.setDayWeek(LocalDate.now())
        festivalViewModel.setWeekFragmentDate(nowWeek)

        val dialogWeekFun : (StartEndDate) -> Unit = { startEndDate -> dialogWeekClick(startEndDate)}
        dialogWeekCalendarFragment = DialogWeekCalendarFragment.newInstance(
            dialogWeekFun
        )
        weekText.setOnClickListener {
            dialogWeekCalendarFragment.show(act.supportFragmentManager,"달력")
        }

        recycler = weekFragmentBinding.weekRecycler
        preBtn.setOnClickListener {
            nowWeek = moveNextWeek(nowWeek)
            weekText.text = setWeekTitleText(nowWeek)

            val result = festivalViewModel.getFestival(
                nowWeek.startEndDateList[nowWeek.weekRow-1].startDate,
                nowWeek.startEndDateList[nowWeek.weekRow-1].endDate
            )

            result.observe(viewLifecycleOwner){
                setWeekView(it)
            }
        }

        nextBtn.setOnClickListener {
            nowWeek = moveNextWeek(nowWeek)
            weekText.text = setWeekTitleText(nowWeek)
            val result = festivalViewModel.getFestival(
                nowWeek.startEndDateList[nowWeek.weekRow-1].startDate,
                nowWeek.startEndDateList[nowWeek.weekRow-1].endDate
            )

            result.observe(viewLifecycleOwner){
                setWeekView(it)
            }
        }



        weekText.text = setWeekTitleText(nowWeek)

        val result = festivalViewModel.getFestival(
            nowWeek.startEndDateList[nowWeek.weekRow-1].startDate,
            nowWeek.startEndDateList[nowWeek.weekRow-1].endDate)
        result.observe(viewLifecycleOwner){
            setWeekView(it)
        }


        return weekFragmentBinding.root
    }
    private fun dialogWeekClick(startEndDate: StartEndDate) {

    }

    private fun movePreWeek(weekInfoDto: WeekInfoDto) : WeekInfoDto {
        if (weekInfoDto.weekRow != 1) {
            weekInfoDto.weekRow--
            return weekInfoDto
        }
        else{
            val preWeekInfoDto = setDayWeek(weekInfoDto.startEndDateList[0].startDate.minusMonths(1))
            preWeekInfoDto.weekRow = preWeekInfoDto.startEndDateList.size
            return preWeekInfoDto
        }

    }




    private fun daysInWeekArray(weekInfo: WeekInfoDto, festivalList : MutableList<FestivalDto>) : MutableList<FestivalWeekInfoDto>{
        val festivalWeekInfoList : MutableList<FestivalWeekInfoDto> = mutableListOf()
        val startDay = weekInfo.startEndDateList[weekInfo.weekRow-1].startDate.dayOfMonth
        val endDay = weekInfo.startEndDateList[weekInfo.weekRow-1].endDate.dayOfMonth
        for (i:Int in startDay until endDay+1){
            val nowDayLocalDate = LocalDate.of(
                weekInfo.startEndDateList[weekInfo.weekRow-1].startDate.year,
                weekInfo.startEndDateList[weekInfo.weekRow-1].startDate.month,
                i
            )
            val festivalSummaryList: MutableList<FestivalSummaryDto> = getFestivalSummaryList(festivalList, nowDayLocalDate)

            val festivalWeekInfoDto = FestivalWeekInfoDto(nowDayLocalDate,festivalSummaryList)
            festivalWeekInfoList.add(festivalWeekInfoDto)
        }
        return festivalWeekInfoList

    }

    private fun getFestivalSummaryList(
        festivalList: MutableList<FestivalDto>,
        nowDayLocalDate: LocalDate?
    ): MutableList<FestivalSummaryDto> {
        val festivalSummaryList: MutableList<FestivalSummaryDto> = mutableListOf()

        for (festivalDto in festivalList) {
            val festivalStartLocalDate = LocalDate.parse(festivalDto.fstvlStartDate, formatter);
            val festivalEndLocalDate = LocalDate.parse(festivalDto.fstvlEndDate, formatter);
            if (
                (festivalStartLocalDate.isBefore(nowDayLocalDate) || festivalStartLocalDate.isEqual(
                    nowDayLocalDate
                )) &&
                (festivalEndLocalDate.isAfter(nowDayLocalDate) || festivalEndLocalDate.isEqual(
                    nowDayLocalDate
                ))
            ) {
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
        return festivalSummaryList
    }

    private fun setWeekView(festivalList : MutableList<FestivalDto>){


        val dayList = daysInWeekArray(nowWeek,festivalList)

        val funFestivalClickVal : (Long) -> Unit = {id -> festivalClickEvent(id)}
        val adapter = WeekCalendarAdapter(requireContext(),funFestivalClickVal)
        adapter.submitList(dayList)

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        val weekRecycler = weekFragmentBinding.weekRecycler

        weekRecycler.adapter = adapter
        weekRecycler.layoutManager = manager

    }

    private fun calcMonthWeek(month : Int) : MutableList<WeekInfoDto>{
        val weekList : MutableList<WeekInfoDto> = mutableListOf()

        return weekList
    }



    private fun festivalClickEvent(id : Long){
        val result = festivalViewModel.getFestival(id)
        result.observe(viewLifecycleOwner){
            val festivalDetailDto = FestivalDetailDto(it)
            val funUrlOpen : (String) -> Unit =  { url -> urlOpen(url) }
            val dialog = FestivalFragmentDialog(festivalDetailDto,funUrlOpen)
            dialog.show(act.supportFragmentManager,"축제 정보")
        }

    }
    private fun urlOpen(url : String){

    }


    companion object{
        fun newInstance() : WeekFragment {
            return WeekFragment()
        }
    }

}