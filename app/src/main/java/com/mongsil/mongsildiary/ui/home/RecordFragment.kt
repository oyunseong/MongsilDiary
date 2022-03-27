package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentRecordBinding
import com.mongsil.mongsildiary.model.RecordViewModel
import com.mongsil.mongsildiary.utils.log

class RecordFragment : BaseFragment<FragmentRecordBinding>() {
    val recordViewModel : RecordViewModel by activityViewModels()
//    private val recordViewModel: RecordViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecordBinding {
        return FragmentRecordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmBtn.isEnabled = false    // 버튼 비활성화
        binding.confirmBtn.isClickable = false

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editText.length() == 0) {
                    binding.confirmBtn.isEnabled = false    // 버튼 비활성화
                    binding.confirmBtn.isClickable = false
                } else {
                    binding.confirmBtn.isEnabled = true // 버튼 활성화
                    binding.confirmBtn.isClickable = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.confirmBtn.setOnClickListener {
            recordViewModel._contents.value = binding.editText.text.toString()
            view?.findNavController().navigate(R.id.homeFragment)
        }
    }


}