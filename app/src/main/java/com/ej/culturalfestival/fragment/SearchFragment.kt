package com.ej.culturalfestival.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    lateinit var searchFragmentBinding : FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchFragmentBinding = FragmentSearchBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        return searchFragmentBinding.root
    }
    companion object{
        fun newInstance() : SearchFragment {
            return SearchFragment()
        }
    }
}