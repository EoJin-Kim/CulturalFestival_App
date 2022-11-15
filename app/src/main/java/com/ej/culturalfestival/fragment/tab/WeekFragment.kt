package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import android.util.Log
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
import com.ej.culturalfestival.fragment.dialog.WeekCalendarFragmentDialog
import com.ej.culturalfestival.fragment.dialog.FestivalFragmentDialog
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.util.CalendarUtil.Companion.formatter
import com.ej.culturalfestival.util.CalendarUtil.Companion.moveNextOneWeek
import com.ej.culturalfestival.util.CalendarUtil.Companion.movePreOneWeek
import com.ej.culturalfestival.util.CalendarUtil.Companion.setWeekTitleText
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate


class WeekFragment : Fragment() {



    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }

    lateinit var binding : FragmentWeekBinding
    lateinit var weekCalendarFragmentDialog : WeekCalendarFragmentDialog
    lateinit var nowWeek : WeekInfoDto;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeekBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nowWeek = CalendarUtil.setDayWeek(LocalDate.now())
        festivalViewModel.setWeekFragmentDate(nowWeek)
        drawUi()

        val dialogWeekFun : (StartEndDate,Int) -> Unit = { startEndDate ,position-> dialogWeekClick(startEndDate,position)}
        weekCalendarFragmentDialog = WeekCalendarFragmentDialog.newInstance(
            dialogWeekFun
        )
        binding.weekTitle.setOnClickListener {
            weekCalendarFragmentDialog.show(act.supportFragmentManager,"달력")
        }

        binding.preWeek.setOnClickListener {
            clickPreBtn()
        }

        binding.nextWeek.setOnClickListener {
            clickNextBtn()
        }

        festivalViewModel.festivalSearchResult.observe(viewLifecycleOwner){
            setWeekView(it)
        }
        festivalViewModel.getFestival(
            nowWeek.startEndDateList[nowWeek.weekRow-1].startDate,
            nowWeek.startEndDateList[nowWeek.weekRow-1].endDate
        )

    }

    private fun clickNextBtn() {
        nowWeek = moveNextOneWeek(nowWeek)
        festivalViewModel.setWeekFragmentDate(nowWeek)
        binding.weekTitle.text = setWeekTitleText(nowWeek)
        festivalViewModel.getFestival(
            nowWeek.startEndDateList[nowWeek.weekRow - 1].startDate,
            nowWeek.startEndDateList[nowWeek.weekRow - 1].endDate
        )
    }

    private fun clickPreBtn() {
        nowWeek = movePreOneWeek(nowWeek)
        festivalViewModel.setWeekFragmentDate(nowWeek)
        binding.weekTitle.text = setWeekTitleText(nowWeek)

        festivalViewModel.getFestival(
            nowWeek.startEndDateList[nowWeek.weekRow - 1].startDate,
            nowWeek.startEndDateList[nowWeek.weekRow - 1].endDate
        )
    }

    private fun drawUi() {
        binding.weekTitle.text = setWeekTitleText(nowWeek)
    }

    private fun dialogWeekClick(startEndDate: StartEndDate, position : Int) {
        Log.d("click","click")
        Log.d("click","${startEndDate.startDate} ~ ${startEndDate.endDate}")
        binding.weekTitle.text = CalendarUtil.setWeekTitleText(position, startEndDate)
        nowWeek = CalendarUtil.setDayWeek(startEndDate)
        festivalViewModel.setWeekFragmentDate(nowWeek)
        festivalViewModel.getFestival(startEndDate.startDate,startEndDate.endDate)
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

        festivalViewModel.festivalOne.observe(viewLifecycleOwner){
            val festivalDetailDto = FestivalDetailDto(it)
            val funUrlOpen : (String) -> Unit =  { url -> urlOpen(url) }
            val dialog = FestivalFragmentDialog.newInstance(festivalDetailDto,funUrlOpen)
            dialog.show(act.supportFragmentManager,"축제 정보")
        }

        val funFestivalClickVal : (Long) -> Unit = {id -> festivalClickEvent(id)}
        val adapter = WeekCalendarAdapter(requireContext(),funFestivalClickVal)
        adapter.submitList(dayList)

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        val weekRecycler = binding.weekRecycler

        weekRecycler.apply {
            this.adapter = adapter
            layoutManager = manager
        }

    }

    private fun calcMonthWeek(month : Int) : MutableList<WeekInfoDto>{
        val weekList : MutableList<WeekInfoDto> = mutableListOf()
        return weekList
    }



    private fun festivalClickEvent(id : Long){
        festivalViewModel.getFestival(id)
    }
    private fun urlOpen(url : String){

    }


    companion object{
        fun newInstance() : WeekFragment {
            return WeekFragment()
        }
    }

}