package com.ej.culturalfestival.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.R
import com.ej.culturalfestival.adapter.DayCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentSearchBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.viewmodel.FestivalViewModel


class SearchFragment : Fragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
    lateinit var searchFragmentBinding : FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchFragmentBinding = FragmentSearchBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        val searchResultList = festivalViewModel.festivalSearchResult.value
        if(searchResultList!!.size >0){
            searchFragmentBinding.noResult.visibility = View.GONE
            searchFragmentBinding.searchFestivalCount.text = "${searchResultList.size} 축제"
            setRecycler(searchResultList)
        }
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return searchFragmentBinding.root
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
        val recycler = searchFragmentBinding.searchRecycler
        recycler.layoutManager = manager

        //어뎁터 적용
        recycler.adapter = adapter
    }

    fun openHomepage(url : String){
        Log.d("button","homepage")
        festivalViewModel.openUrl = url
        act.setFragment("homepage")
    }

    companion object{
        fun newInstance() : SearchFragment {
            return SearchFragment()
        }
    }
}