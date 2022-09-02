package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentDialogMonthCalendarBinding


class DialogMonthCalendarFragment : DialogFragment() {

    lateinit var dialogMonthCalendarFragmentBinding : FragmentDialogMonthCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialogMonthCalendarFragmentBinding = FragmentDialogMonthCalendarBinding.inflate(layoutInflater)
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

    companion object {

        fun newInstance() : DialogMonthCalendarFragment{
            return DialogMonthCalendarFragment()
        }

    }
}