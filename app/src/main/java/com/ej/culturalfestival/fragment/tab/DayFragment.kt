package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DayCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDayBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.fragment.dialog.DayCalendarFragmentDialog
import com.ej.culturalfestival.util.CalendarUtil.Companion.monthDayFromDate
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate


class DayFragment : Fragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by activityViewModels()
    lateinit var dayCalendarFragmentDialog: DayCalendarFragmentDialog
    var nowLocalDate : LocalDate = LocalDate.now()

    lateinit var binding: FragmentDayBinding
    lateinit var nowDayText: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawUi()

        val dialogDayFun : (LocalDate) -> Unit = { date -> dialogDayClick(date)}
        dayCalendarFragmentDialog = DayCalendarFragmentDialog.newInstance(dialogDayFun)

        binding.nowDayText.setOnClickListener {
            Log.d("click","click1")
            dayCalendarFragmentDialog.show(act.supportFragmentManager,"축제 정보")
        }


        festivalViewModel.dayFragmentDate.observe(viewLifecycleOwner){
            nowLocalDate = it
            val dateStr = monthDayFromDate(it)
            binding.nowDayText.text = dateStr
            getFestival(it)
        }
        festivalViewModel.setDayFragmentDate(LocalDate.now())

        binding.preDay.setOnClickListener {
            clickPreDay()
        }

        binding.nextDay.setOnClickListener {
            clickNextDay()
        }
    }

    private fun drawUi() {
        binding.nowDayText.text = monthDayFromDate(LocalDate.now())
    }

    private fun dialogDayClick(date :LocalDate){
        festivalViewModel.festivalSearchResult.observe(viewLifecycleOwner){
            nowDayText.text = monthDayFromDate(date)
            festivalViewModel.setDayFragmentDate(date)
            setRecycler(it)
        }
        festivalViewModel.getFestival(date,date)

        Log.d("click","$date")
    }

    private fun clickPreDay(){
        val moveDay = nowLocalDate.minusDays(1)
        festivalViewModel.setDayFragmentDate(moveDay)
        val dateStr = monthDayFromDate(moveDay)
        nowLocalDate=moveDay
        binding.nowDayText.text = dateStr

        // 뷰모델에서 해당 날짜 축제 가지고 오로 submit
    }

    private fun clickNextDay(){
        val moveDay = nowLocalDate.plusDays(1)
        festivalViewModel.setDayFragmentDate(moveDay)
        val dateStr = monthDayFromDate(moveDay)
        nowLocalDate=moveDay
        binding.nowDayText.text = dateStr

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
        val recycler = binding.dayRecycler
        recycler.layoutManager = manager

        //어뎁터 적용
        recycler.adapter = adapter
    }



    fun getFestival(date : LocalDate){
        festivalViewModel.festivalSearchResult.observe(viewLifecycleOwner){
            binding.dayFestivalCount.text = "${it.size} 축제"
            setRecycler(it)
        }
        festivalViewModel.getFestival(date,date)
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