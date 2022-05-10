package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentRecordBinding
import com.mongsil.mongsildiary.model.FabViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        fabViewModel.setFabState(false)

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.toolbar.uploadBtn.setOnClickListener {
            recordViewModel.setContents(binding.editText.text.toString())
            view.findNavController().navigate(R.id.action_recordFragment_to_homeFragment)
        }


//        binding.confirmBtn.isEnabled = false    // 버튼 비활성화
//        binding.confirmBtn.isClickable = false

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editText.length() == 0) {
//                    binding.confirmBtn.isEnabled = false    // 버튼 비활성화
//                    binding.confirmBtn.isClickable = false
                } else {
//                    binding.confirmBtn.isEnabled = true // 버튼 활성화
//                    binding.confirmBtn.isClickable = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}