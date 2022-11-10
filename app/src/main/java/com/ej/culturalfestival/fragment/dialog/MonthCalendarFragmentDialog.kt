package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ej.culturalfestival.adapter.DialogMonthCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogMonthCalendarBinding


class MonthCalendarFragmentDialog(
) : DialogFragment() {
    lateinit var dialogMonthClick : (Int) -> Unit
    lateinit var binding : FragmentDialogMonthCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogMonthCalendarBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
    }


    override fun onResume() {
        super.onResume()

        // dialog 넓이 80% 설정
        val params = dialog?.window?.attributes
        params?.width = resources.displayMetrics.widthPixels * 9 /10
//        params?.height = resources.displayMetrics.heightPixels * 5 /10
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun setRecycler() {
        val adapter = DialogMonthCalendarAdapter(dialogMonthClick,this)
        val arrayList = mutableListOf<Int>()
        for (i in 1 until 13) {
            arrayList.add(i)
        }
        adapter.submitList(arrayList)

        val manager = GridLayoutManager(requireContext(), 3)
        val recycler = binding.dialogMonthRecycler
        recycler.apply {
            this.adapter = adapter
            layoutManager = manager
        }
    }

    companion object {

        fun newInstance(dialogMonthFun : (Int) -> Unit) =
            MonthCalendarFragmentDialog().apply {
                dialogMonthClick = dialogMonthFun
            }
    }
}