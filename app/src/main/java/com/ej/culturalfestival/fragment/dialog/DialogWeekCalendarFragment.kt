package com.ej.culturalfestival.fragment.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentDialogWeekCalendarBinding


class DialogWeekCalendarFragment : Fragment() {

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
        return dialogWeekCalendarFragmentBinding.root
    }

    companion object {

        fun newInstance() : DialogWeekCalendarFragment{
            return DialogWeekCalendarFragment()
        }

    }
}