package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mongsil.mongsildiary.HomeAdapter
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentWritingTimeslotBinding

class TimeSlotFragment : BaseFragment<FragmentWritingTimeslotBinding>() {
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val mainAdapter = HomeAdapter(dataSet, object : HomeAdapter.OnItemClickListener{
        override fun onClick(v: View, position: Int) {
            showToast("${position}번째 item")
        }
    })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWritingTimeslotBinding {
        return FragmentWritingTimeslotBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager2.adapter = mainAdapter

        binding.confirmBtn.isEnabled = false    // 버튼 비활성화
        binding.confirmBtn.isClickable = false

        binding.editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.editText.length() == 0){   // 2자리 미만이면
                    binding.confirmBtn.isEnabled = false    // 버튼 비활성화
                    binding.confirmBtn.isClickable = false
                }else{
                    binding.confirmBtn.isEnabled = true // 버튼 활성화
                    binding.confirmBtn.isClickable = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.confirmBtn.setOnClickListener {
            if(it.isEnabled) {
                showToast("클릭 되었습니다.")
            }else{

            }
        }
    }


//    val editText = object : androidx.appcompat.widget.AppCompatEditText(requireContext()){
//        override fun onCreateInputConnection(editorInfo: EditorInfo): InputConnection? {
//            val ic: InputConnection? = super.onCreateInputConnection(editorInfo)
//            EditorInfoCompat.setContentMimeTypes(editorInfo,arrayOf("image/png"))
//
//            val callback =
//                InputConnectionCompat.OnCommitContentListener{ inputContentInfo, flags, opts ->
//                    val lacksPermission = (flags and
//                            InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 && lacksPermission){
//                        try {
//                            inputContentInfo.requestPermission()
//                        }catch (e: Exception){
//                            return@OnCommitContentListener false // return false if failed
//                        }
//                    }
//                    true
//                }
//            return ic?.let { InputConnectionCompat.createWrapper(it,editorInfo,callback) }
//        }
//    }
}