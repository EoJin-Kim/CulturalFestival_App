package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DayCalendarAdapter
import com.ej.culturalfestival.adapter.MonthCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDayBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DayFragment : Fragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }

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
        nowDayText.text = dayFromDate(CalendarUtil.selectedDate)

        val result = festivalViewModel.getFestival(CalendarUtil.selectedDate,CalendarUtil.selectedDate)
        result.observe(viewLifecycleOwner){
            setRecycler(it)
        }


        return dayFragmentBinding.root
    }


    private fun setRecycler(festivalList: MutableList<FestivalDto>){


        // 해당 월 날짜 가져오기
        val festivalDetialList : MutableList<FestivalDetailDto> = mutableListOf()

        for (festivalDto in festivalList) {
            val festivalDetail = FestivalDetailDto(festivalDto.id,festivalDto.fstvlNm)
            festivalDetialList.add(festivalDetail)
        }

        // 어뎁터 데이터 적용
        val adapter = DayCalendarAdapter()
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

    companion object{
        fun newInstance() : DayFragment {
            return DayFragment()
        }
    }
}