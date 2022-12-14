package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.adapter.DialogDayCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogDayCalendarBinding
import com.ej.culturalfestival.util.CalendarUtil.Companion.dateListTodateDtoList
import com.ej.culturalfestival.util.CalendarUtil.Companion.yearMonthFromDate
import com.ej.culturalfestival.viewmodel.FestivalViewModel
import java.time.LocalDate
import java.time.YearMonth


class DayCalendarFragmentDialog(
    private val dayOnClick : (LocalDate) -> Unit,
) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by activityViewModels()

    lateinit var binding : FragmentDialogDayCalendarBinding

    lateinit var dialogDayDate : LocalDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogDayCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogDayDate = festivalViewModel.dayFragmentDate.value!!
        drawUi()
        binding.dialogDayPreBtn.setOnClickListener {
            dayPreButtonClick()
        }
        binding.dialogDayNextBtn.setOnClickListener {
            dayNextButtonClick()
        }

    }

    private fun drawUi() {
        binding.dialogDayDateText.text = yearMonthFromDate(dialogDayDate)
        setRecycler(festivalViewModel.dayFragmentDate.value!!)
    }

    private fun dayNextButtonClick() {
        dialogDayDate = dialogDayDate.plusMonths(1)
        binding.dialogDayDateText.text = yearMonthFromDate(dialogDayDate)
        setRecycler(dialogDayDate)
    }

    private fun dayPreButtonClick() {
        dialogDayDate = dialogDayDate.minusMonths(1)
        binding.dialogDayDateText.text = yearMonthFromDate(dialogDayDate)
        setRecycler(dialogDayDate)
    }

    private fun setRecycler(date : LocalDate) {
        val adapter = DialogDayCalendarAdapter(dayOnClick,this)

        val dateList = daysInMonthArray(date)

        val dateDtoList = dateListTodateDtoList(dateList)
        adapter.submitList(dateDtoList)

        // ???????????? ?????? (??? 7???)
        val manager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)

        val recyclerView = binding.dialogMonthRecycler
        // ???????????? ??????
        recyclerView.layoutManager = manager

        //????????? ??????
        recyclerView.adapter = adapter
    }



    private fun daysInMonthArray(date : LocalDate) : MutableList<LocalDate?>{
        val dayList :MutableList<LocalDate?> = mutableListOf()
        val yearMonth = YearMonth.from(date)

        // ?????? ??? ????????? ?????? ????????????(??? 28, 30, 31)
        val lastDay : Int = yearMonth.lengthOfMonth()

        // ?????? ?????? ??? ?????? ??? ???????????? (??? 4???1???)
        val firstDay : LocalDate = date.withDayOfMonth(1)

        //??? ?????? ??? ?????? ????????????(???:1, ???:7)
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
        // dialog ?????? 80% ??????
        val params = dialog?.window?.attributes
        params?.width = resources.displayMetrics.widthPixels * 9 /10
//        params?.height = resources.displayMetrics.heightPixels * 5 /10
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    companion object {
        fun newInstance(dayOnclick : (LocalDate) -> Unit) : DayCalendarFragmentDialog{
            return DayCalendarFragmentDialog(
                dayOnclick
            )
        }

    }
}