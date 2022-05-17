package com.mongsil.mongsildiary.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

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
    val recordViewModel: RecordViewModel by activityViewModels()
    val REQUEST_CODE = 0

    private lateinit var getResult:ActivityResultLauncher<Intent>

    //    private val recordViewModel: RecordViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecordBinding {
        return FragmentRecordBinding.inflate(inflater, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabViewModel.setFabState(false)


        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            "resultCode : ${it.resultCode}".log()
            "RESULT_OK : ${RESULT_OK}".log()
            if (it.resultCode == RESULT_OK){
                showToast("예외!")
            }else
            {
                showToast("나콜백!!!")
            }
        }

        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            getResult.launch(intent)
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, REQUEST_CODE)
        }


        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.toolbar.uploadBtn.setOnClickListener {
            "클릭".log()
            if (binding.editText.length() == 0) {
                binding.toolbar.uploadBtn.isEnabled = false // 버튼 비활성화
                binding.toolbar.uploadBtn.isClickable = false
            } else {
                recordViewModel.setContents(binding.editText.text.toString())
                view.findNavController().navigate(R.id.action_recordFragment_to_homeFragment)
            }
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                "${binding.editText.length()}".log()
                // EditText에 아무것도 안 써있으면 우측 상단 등록 버튼 비활성화
                if (binding.editText.length() == 0) {
                    binding.toolbar.uploadBtn.isEnabled = false // 버튼 비활성화
                    binding.toolbar.uploadBtn.isClickable = false
                    binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#d5d9e2"))
                    binding.blankImage.visibility = View.VISIBLE
                    binding.blank1Tv.visibility = View.VISIBLE
                    binding.blank2Tv.visibility = View.VISIBLE
                } else {
                    binding.toolbar.uploadBtn.isEnabled = true // 버튼 활성화
                    binding.toolbar.uploadBtn.isClickable = true
                    binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#7ea1ff"))
                    binding.blankImage.visibility = View.GONE
                    binding.blank1Tv.visibility = View.GONE
                    binding.blank2Tv.visibility = View.GONE
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