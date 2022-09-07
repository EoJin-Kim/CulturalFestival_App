package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.R
import com.ej.culturalfestival.adapter.DialogMonthCalendarAdapter
import com.ej.culturalfestival.adapter.DialogWeekCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogWeekCalendarBinding
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.util.CalendarUtil
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate


class DialogWeekCalendarFragment(private val weekOnClick : (StartEndDate) -> Unit) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
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

        val weekInfo= CalendarUtil.setDayWeek(festivalViewModel.weekFragmentDate.value!!)
        setRecycler(weekInfo.startEndDateList)


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

    companion object {

        fun newInstance(dialogWeekFun : (StartEndDate) -> Unit) : DialogWeekCalendarFragment{
            return DialogWeekCalendarFragment(dialogWeekFun)
        }

    }
}