package com.mongsil.mongsildiary.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentRecordBinding
import com.mongsil.mongsildiary.model.RecordViewModel
import com.mongsil.mongsildiary.utils.log

class RecordFragment : BaseFragment<FragmentRecordBinding>() {
    val recordViewModel: RecordViewModel by activityViewModels()
    private val REQUEST_CODE = 1

    private lateinit var getResult: ActivityResultLauncher<Intent>

    //    private val recordViewModel: RecordViewModel by viewModels()

    private fun openGallery() {
        //TODO 권한 요청
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    /*
    1. 이미지 클릭시 data에 uri값으로 전달
    2. uri값을 bitmap으로 변환해야함 ( getBitmap 함수)

    onActivityResult()는 갤러리앱에서 몽실 앱으로 돌아올 때 호출
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                val currentImageUrl: Uri? = data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        context!!.contentResolver,
                        currentImageUrl
                    )
                    binding.firstImageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    "에러뜨넹 : ${e.printStackTrace()}".log()
                }
            } else {
                "something wrong".log("ActivityResult")
            }
        } else {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecordBinding {
        return FragmentRecordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        fabViewModel.setFabState(false)

//        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            "resultCode : ${it.resultCode}".log()
//            "RESULT_OK : ${RESULT_OK}".log()
//            if (it.resultCode == RESULT_OK){
//
//            }else
//            {
//                showToast("나콜백!!!")
//            }
//        }

        binding.galleryBtn.setOnClickListener {
            openGallery()
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