package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DialogWeekCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogWeekCalendarBinding
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.dto.WeekInfoDto
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel


class WeekCalendarFragmentDialog(private val weekOnClick : (StartEndDate, Int) -> Unit) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by activityViewModels()
    lateinit var weekInfoDto: WeekInfoDto
    lateinit var binding : FragmentDialogWeekCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogWeekCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekInfoDto = festivalViewModel.weekFragmentDate.value!!

        drawUi()

        binding.dialogWeekPreBtn.setOnClickListener {
            monthPreButtonClick()
        }

        binding.dialogWeekNextBtn.setOnClickListener {
            monthNextButtonClick()
        }
    }

    private fun monthNextButtonClick() {
        weekInfoDto = CalendarUtil.moveNextOneMonth(weekInfoDto)
        setTitle(weekInfoDto)
        setRecycler(weekInfoDto.startEndDateList)
    }

    private fun monthPreButtonClick() {
        weekInfoDto = CalendarUtil.movePreOneMonth(weekInfoDto)
        setTitle(weekInfoDto)
        setRecycler(weekInfoDto.startEndDateList)
    }

    private fun drawUi() {
        setRecycler(weekInfoDto.startEndDateList)
        setTitle(weekInfoDto)
    }

    override fun onResume() {
        super.onResume()
        // dialog 넓이 80% 설정
        val params = dialog?.window?.attributes
        params?.width = resources.displayMetrics.widthPixels * 9 /10
//        params?.height = resources.displayMetrics.heightPixels * 5 /10
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun setRecycler(startEndDateList : MutableList<StartEndDate>){
        val adapter = DialogWeekCalendarAdapter(weekOnClick,this)
        adapter.submitList(startEndDateList)
        val recycler = binding.dialogWeekRecycler


        val manager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        recycler.layoutManager = manager
        recycler.adapter = adapter
    }

    private fun setTitle(weekInfoDto: WeekInfoDto){
        val month = weekInfoDto.startEndDateList[0].startDate.month.value
        binding.dialogWeekDateText.text = "${month}월"

    }

    companion object {
        fun newInstance(dialogWeekFun : (StartEndDate,Int) -> Unit) : WeekCalendarFragmentDialog{
            return WeekCalendarFragmentDialog(dialogWeekFun)
        }

    }
}