package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DayCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDayBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.fragment.dialog.DialogMonthCalendarFragment
import com.ej.culturalfestival.fragment.dialog.FestivalFragmentDialog
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DayFragment : Fragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
    lateinit var dialogMonthCalendarFragment: DialogMonthCalendarFragment
    var nowLocalDate : LocalDate = LocalDate.now()

    lateinit var dayFragmentBinding: FragmentDayBinding

    lateinit var nowDayText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dayFragmentBinding = FragmentDayBinding.inflate(inflater,container,false)
        nowDayText = dayFragmentBinding.nowDayText
        nowDayText.text = dayFromDate(LocalDate.now())

//        val result = festivalViewModel.getFestival(CalendarUtil.selectedDate,CalendarUtil.selectedDate)
//        result.observe(viewLifecycleOwner){
//            setRecycler(it)
//        }


        val dialogDayFun : (LocalDate) -> Unit = { date -> dialogDayClick(date)}
        dialogMonthCalendarFragment = DialogMonthCalendarFragment.newInstance(
            dialogDayFun
        )

        dayFragmentBinding.nowDayText.setOnClickListener {
            dialogMonthCalendarFragment.show(act.supportFragmentManager,"축제 정보")
        }



        val dayFragmentDate = festivalViewModel.dayFragmentDate




        dayFragmentDate.observe(viewLifecycleOwner){
            nowLocalDate = it
            val dateStr = dayFromDate(it)
            dayFragmentBinding.nowDayText.text = dateStr
            getFestival(it)
        }
        if(dayFragmentDate.value==null){
            festivalViewModel.setDayFragmentDate(LocalDate.now())
        }

        dayFragmentBinding.preDay.setOnClickListener {
            movePreDay()
        }

        dayFragmentBinding.nextDay.setOnClickListener {
            moveNextDay()
        }


        return dayFragmentBinding.root
    }

    private fun dialogDayClick(date :LocalDate){
        Log.d("click","click")
    }

    private fun movePreDay(){
        val moveDay = nowLocalDate.minusDays(1)
        festivalViewModel.setDayFragmentDate(moveDay)
        val dateStr = dayFromDate(moveDay)
        nowLocalDate=moveDay
        dayFragmentBinding.nowDayText.text = dateStr

        // 뷰모델에서 해당 날짜 축제 가지고 오로 submit
    }

    private fun moveNextDay(){
        val moveDay = nowLocalDate.plusDays(1)
        festivalViewModel.setDayFragmentDate(moveDay)
        val dateStr = dayFromDate(moveDay)
        nowLocalDate=moveDay
        dayFragmentBinding.nowDayText.text = dateStr

        // 뷰모델에서 해당 날짜 축제 가지고 오로 submit
    }


    private fun setRecycler(festivalList: MutableList<FestivalDto>){


        // 해당 월 날짜 가져오기
        val festivalDetialList : MutableList<FestivalDetailDto> = mutableListOf()

        for (festivalDto in festivalList) {
            val festivalDetail = FestivalDetailDto(
                festivalDto.id,
                festivalDto.fstvlNm,
                festivalDto.fstvlCo,
                festivalDto.phoneNumber,
                festivalDto.auspcInstt,
                festivalDto.suprtInstt,
                festivalDto.rdnmadr,
                festivalDto.lnmadr,
                "${festivalDto.fstvlStartDate} ~ ${festivalDto.fstvlEndDate}",
                festivalDto.homepageUrl
            )
            festivalDetialList.add(festivalDetail)
        }

        // 어뎁터 데이터 적용
        val funOpenHompage : (String) -> Unit = {url -> openHomepage(url)}
        val adapter = DayCalendarAdapter(funOpenHompage)
        adapter.submitList(festivalDetialList)

        val manager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())


        // 레이아웃 적용
        val recycler = dayFragmentBinding.dayRecycler
        recycler.layoutManager = manager

        //어뎁터 적용
        recycler.adapter = adapter
    }

    fun dayFromDate(date : LocalDate) : String{
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MM월 dd일")
        return date.format(formatter)
    }

    fun getFestival(date : LocalDate){
        val result = festivalViewModel.getFestival(date,date)
        result.observe(viewLifecycleOwner){
            dayFragmentBinding.dayFestivalCount.text = "${it.size} 축제"
            setRecycler(it)
        }
    }

    fun openHomepage(url : String){
        Log.d("button","homepage")
        festivalViewModel.openUrl = url
        act.setFragment("homepage")
    }

    companion object{
        fun newInstance() : DayFragment {
            return DayFragment()
        }
    }
}