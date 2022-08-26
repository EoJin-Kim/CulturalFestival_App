package com.ej.culturalfestival.fragment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentFestivalDialogBinding
import com.ej.culturalfestival.databinding.RowDayFestivalItemBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.response.FestivalDto


class FestivalFragmentDialog(
    private val festivalDetailDto: FestivalDetailDto,
    private val onclick : (url : String) -> Unit,
    ) : DialogFragment() {

    lateinit var rowDayFestivalItemBinding: RowDayFestivalItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rowDayFestivalItemBinding = RowDayFestivalItemBinding.inflate(layoutInflater)
        // dialog 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        rowDayFestivalItemBinding.dayFestivalTitle.text =festivalDetailDto.title
        rowDayFestivalItemBinding.dayFestivalContent.text =festivalDetailDto.content
        rowDayFestivalItemBinding.dayFestivalPhone.text =festivalDetailDto.phone
        rowDayFestivalItemBinding.dayFestivalAddress.text =festivalDetailDto.address
        rowDayFestivalItemBinding.dayFestivalDate.text =festivalDetailDto.date


        return rowDayFestivalItemBinding.root
    }

    override fun onResume() {
        super.onResume()

        // dialog 넓이 80% 설정
        val params = dialog?.window?.attributes
        params?.width = resources.displayMetrics.widthPixels * 8 /10
//        params?.height = resources.displayMetrics.heightPixels * 5 /10
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}