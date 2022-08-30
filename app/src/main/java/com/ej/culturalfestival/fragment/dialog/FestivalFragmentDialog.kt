package com.ej.culturalfestival.fragment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.R
import com.ej.culturalfestival.databinding.FragmentFestivalDialogBinding
import com.ej.culturalfestival.databinding.RowDayFestivalItemBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.dto.response.FestivalDto
import com.ej.culturalfestival.viewmodel.FestivalViewModel


class FestivalFragmentDialog(
    private val festivalDetailDto: FestivalDetailDto,
    private val onclick : (url : String) -> Unit,
    ) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by lazy { ViewModelProvider(act).get(FestivalViewModel::class.java) }
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

        if (festivalDetailDto.lnmadr=="" && festivalDetailDto.rdnmadr==""){
            rowDayFestivalItemBinding.dayFestivalAddress.visibility = View.GONE
        }
        else{
            if(festivalDetailDto.lnmadr!=""){
                rowDayFestivalItemBinding.dayFestivalAddress.text = festivalDetailDto.lnmadr
            }
            else{
                rowDayFestivalItemBinding.dayFestivalAddress.text = festivalDetailDto.rdnmadr
            }
        }

        if(festivalDetailDto.auspcInstt==""){
            rowDayFestivalItemBinding.dayAuspcInsttLayout.visibility = View.GONE
        }
        else{
            rowDayFestivalItemBinding.dayFestivalAuspcInstt.text = festivalDetailDto.auspcInstt
        }

        if(festivalDetailDto.suprtInstt==""){
            rowDayFestivalItemBinding.daySuprtInsttLayout.visibility = View.GONE
        }
        else{
            rowDayFestivalItemBinding.dayFestivalSuprtInstt.text = festivalDetailDto.suprtInstt
        }

        rowDayFestivalItemBinding.dayFestivalDate.text =festivalDetailDto.date

        if(festivalDetailDto.homepage!=""){
            rowDayFestivalItemBinding.dayFestivalHompage.setOnClickListener {
                festivalViewModel.openUrl = festivalDetailDto.homepage
                act.setFragment("homepage")
                dismiss()
            }
        }
        else{
            rowDayFestivalItemBinding.dayFestivalHompage.visibility = View.GONE
        }



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