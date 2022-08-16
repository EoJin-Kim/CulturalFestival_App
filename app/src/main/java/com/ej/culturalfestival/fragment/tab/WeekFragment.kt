package com.ej.culturalfestival.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentWeekBinding


class WeekFragment : Fragment() {



    lateinit var weekFragmentBinding : FragmentWeekBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        weekFragmentBinding = FragmentWeekBinding.inflate(inflater,container,false)
        return weekFragmentBinding.root
    }

    companion object{
        fun newInstance() : WeekFragment {
            return WeekFragment()
        }
    }
}