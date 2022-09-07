package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DialogMonthCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogDayCalendarBinding
import com.ej.culturalfestival.util.CalendarUtil.Companion.dateListTodateDtoList
import com.ej.culturalfestival.util.CalendarUtil.Companion.yearMonthFromDate
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.YearMonth


class DialogDayCalendarFragment(
    private val dayOnClick : (LocalDate) -> Unit,
) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }

    lateinit var dialogDayCalendarFragmentBinding : FragmentDialogDayCalendarBinding

    lateinit var dialogDateText : TextView
    lateinit var dialogDayDate : LocalDate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialogDayCalendarFragmentBinding = FragmentDialogDayCalendarBinding.inflate(layoutInflater)
        dialogDayDate = festivalViewModel.dayFragmentDate.value!!


        dialogDateText = dialogDayCalendarFragmentBinding.dialogDayDateText

        dialogDateText.text = yearMonthFromDate(LocalDate.now())

        dialogDayCalendarFragmentBinding.dialogDayPreBtn.setOnClickListener {

            dialogDayDate = dialogDayDate.minusMonths(1)
//            festivalViewModel.setDayFragmentDate(preDate)
            dialogDateText.text = yearMonthFromDate(dialogDayDate)
            setRecycler(dialogDayDate)
        }

        dialogDayCalendarFragmentBinding.dialogDayNextBtn.setOnClickListener {
            dialogDayDate = dialogDayDate.plusMonths(1)
//            festivalViewModel.setDayFragmentDate(nextDate)
            dialogDateText.text = yearMonthFromDate(dialogDayDate)
            setRecycler(dialogDayDate)
        }




        setRecycler(festivalViewModel.dayFragmentDate.value!!)

        return dialogDayCalendarFragmentBinding.root
    }

    private fun setRecycler(date : LocalDate) {
        val adapter = DialogMonthCalendarAdapter(dayOnClick,this)

        val dateList = daysInMonthArray(date)

        val dateDtoList = dateListTodateDtoList(dateList)
        adapter.submitList(dateDtoList)

        // 레이아웃 설정 (열 7개)
        val manager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)

        val recyclerView = dialogDayCalendarFragmentBinding.dialogMonthRecycler
        // 레이아웃 적용
        recyclerView.layoutManager = manager

        //어뎁터 적용
        recyclerView.adapter = adapter
    }



    private fun daysInMonthArray(date : LocalDate) : MutableList<LocalDate?>{
        val dayList :MutableList<LocalDate?> = mutableListOf()
        val yearMonth = YearMonth.from(date)

        // 해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        val lastDay : Int = yearMonth.lengthOfMonth()

        // 해당 월의 첫 번째 날 가져오기 (예 4월1일)
        val firstDay : LocalDate = date.withDayOfMonth(1)

        //첫 번째 날 요일 가져오기(월:1, 일:7)
        var dayOfWeek : Int = firstDay.dayOfWeek.value


        var calendarSize = lastDay
        if (dayOfWeek != 7) {
            calendarSize += dayOfWeek
        }
        val nextMonthDay = if (calendarSize%7 ==0)  0 else (7-calendarSize%7)
        calendarSize+=nextMonthDay

        if (dayOfWeek == 7) {
            dayOfWeek =0
        }

        for (i in 1 until (calendarSize+1)) {
            if (i <= dayOfWeek ) {
                dayList.add(null)


            }
            else if( i > lastDay + dayOfWeek){
                dayList.add(null)

            }
            else{

                val nowDate = LocalDate.of(
                    date.year,
                    date.month,i-dayOfWeek)
                dayList.add(nowDate)

            }
        }

        return dayList
    }

    override fun onResume() {
        super.onResume()
        // dialog 넓이 80% 설정
        val params = dialog?.window?.attributes
        params?.width = resources.displayMetrics.widthPixels * 9 /10
//        params?.height = resources.displayMetrics.heightPixels * 5 /10
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    companion object {

        fun newInstance(dayOnclick : (LocalDate) -> Unit) : DialogDayCalendarFragment{
            return DialogDayCalendarFragment(
                dayOnclick
            )
        }

    }
}