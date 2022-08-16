package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentDayBinding
import com.ej.culturalfestival.fragment.CalendarFagment


class DayFragment : Fragment() {

    lateinit var dayFragmentBinding: FragmentDayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dayFragmentBinding = FragmentDayBinding.inflate(inflater,container,false)
        return dayFragmentBinding.root
    }

    companion object{
        fun newInstance() : DayFragment {
            return DayFragment()
        }
    }
}