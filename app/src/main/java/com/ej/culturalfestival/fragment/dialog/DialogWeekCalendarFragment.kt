package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DialogWeekCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogWeekCalendarBinding
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.dto.WeekInfoDto
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel


class DialogWeekCalendarFragment(private val weekOnClick : (StartEndDate,Int) -> Unit) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
    lateinit var weekInfoDto: WeekInfoDto
    lateinit var dialogWeekCalendarFragmentBinding : FragmentDialogWeekCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialogWeekCalendarFragmentBinding = FragmentDialogWeekCalendarBinding.inflate(layoutInflater)
        weekInfoDto = festivalViewModel.weekFragmentDate.value!!
        setRecycler(weekInfoDto.startEndDateList)
        setTitle(weekInfoDto)

        dialogWeekCalendarFragmentBinding.dialogWeekPreBtn.setOnClickListener {
            weekInfoDto = CalendarUtil.movePreOneMonth(weekInfoDto)
            setTitle(weekInfoDto)
            setRecycler(weekInfoDto.startEndDateList)
        }

        dialogWeekCalendarFragmentBinding.dialogWeekNextBtn.setOnClickListener {
            weekInfoDto = CalendarUtil.moveNextOneMonth(weekInfoDto)
            setTitle(weekInfoDto)
            setRecycler(weekInfoDto.startEndDateList)
        }


        return dialogWeekCalendarFragmentBinding.root
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
        val recycler = dialogWeekCalendarFragmentBinding.dialogWeekRecycler


        val manager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        recycler.layoutManager = manager
        recycler.adapter = adapter
    }

    private fun setTitle(weekInfoDto: WeekInfoDto){
        val month = weekInfoDto.startEndDateList[0].startDate.month.value
        dialogWeekCalendarFragmentBinding.dialogWeekDateText.text = "${month}월"

    }

    companion object {

        fun newInstance(dialogWeekFun : (StartEndDate,Int) -> Unit) : DialogWeekCalendarFragment{
            return DialogWeekCalendarFragment(dialogWeekFun)
        }

    }
}