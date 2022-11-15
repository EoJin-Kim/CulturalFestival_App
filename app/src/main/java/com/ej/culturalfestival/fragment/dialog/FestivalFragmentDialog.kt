package com.ej.culturalfestival.fragment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ej.culturalfestival.MainActivity
import com.ej.culturalfestival.databinding.RowDayFestivalItemBinding
import com.ej.culturalfestival.dto.FestivalDetailDto
import com.ej.culturalfestival.viewmodel.FestivalViewModel


class FestivalFragmentDialog(
    private val festivalDetailDto: FestivalDetailDto,
    private val onclick : (url : String) -> Unit,
    ) : DialogFragment() {

    val act : MainActivity by lazy { activity as MainActivity }
    val festivalViewModel : FestivalViewModel by activityViewModels()
    lateinit var binding: RowDayFestivalItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = RowDayFestivalItemBinding.inflate(layoutInflater)
        // dialog 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawUi()
    }

    override fun onResume() {
        super.onResume()
        // dialog 넓이 80% 설정
        val params = dialog?.window?.attributes
        params?.width = resources.displayMetrics.widthPixels * 8 /10
//        params?.height = resources.displayMetrics.heightPixels * 5 /10
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun drawUi() {
        binding.dayFestivalTitle.text = festivalDetailDto.title
        binding.dayFestivalContent.text = festivalDetailDto.content
        binding.dayFestivalPhone.text = festivalDetailDto.phone

        if (festivalDetailDto.lnmadr == "" && festivalDetailDto.rdnmadr == "") {
            binding.dayFestivalAddressLayout.visibility = View.GONE
        } else {
            if (festivalDetailDto.lnmadr != "") {
                binding.dayFestivalAddress.text = festivalDetailDto.lnmadr
            } else {
                binding.dayFestivalAddress.text = festivalDetailDto.rdnmadr
            }
        }

        if (festivalDetailDto.auspcInstt == "") {
            binding.dayAuspcInsttLayout.visibility = View.GONE
        } else {
            binding.dayFestivalAuspcInstt.text = festivalDetailDto.auspcInstt
        }

        if (festivalDetailDto.suprtInstt == "") {
            binding.daySuprtInsttLayout.visibility = View.GONE
        } else {
            binding.dayFestivalSuprtInstt.text = festivalDetailDto.suprtInstt
        }

        binding.dayFestivalDate.text = festivalDetailDto.date

        if (festivalDetailDto.homepage != "") {
            binding.dayFestivalHompage.setOnClickListener {
                festivalViewModel.openUrl = festivalDetailDto.homepage
                act.setFragment("homepage")
                dismiss()
            }
        } else {
            binding.dayFestivalHompage.visibility = View.GONE
        }
    }

    companion object{
        fun newInstance(festivalDetailDto: FestivalDetailDto, onclick : (url : String) -> Unit,) : FestivalFragmentDialog {
            return FestivalFragmentDialog(festivalDetailDto,onclick)
        }
    }
}