package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ej.culturalfestival.R
import com.ej.culturalfestival.adapter.DialogMonthCalendarAdapter
import com.ej.culturalfestival.databinding.FragmentDialogMonthCalendarBinding
import java.time.LocalDate


class DialogMonthCalendarFragment(
) : DialogFragment() {
    lateinit var dialogMonthClick : (Int) -> Unit
    lateinit var dialogMonthCalendarFragmentBinding : FragmentDialogMonthCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialogMonthCalendarFragmentBinding = FragmentDialogMonthCalendarBinding.inflate(inflater)
        setRecycler()
        return dialogMonthCalendarFragmentBinding.root
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


        val recycler = dialogMonthCalendarFragmentBinding.dialogMonthRecycler
        val manager = GridLayoutManager(requireContext(), 3)
        recycler.adapter = adapter
        recycler.layoutManager = manager
    }

    companion object {

        fun newInstance(dialogMonthFun : (Int) -> Unit) =
            DialogMonthCalendarFragment().apply {
                dialogMonthClick = dialogMonthFun
            }
    }
}