package com.mongsil.mongsildiary.ui.home.record

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentRecordBinding
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.printLog

class RecordFragment : BaseFragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var record: Record
    private lateinit var bitmap: Bitmap
    private val recordViewModel by viewModels<RecordViewModel>()

    companion object {
        private const val REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun openGallery() {
        val writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_CODE
            )
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
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
                    bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        currentImageUrl
                    )
//                    recordViewModel.setRecord(record.copy(images = bitmap))
//                    "${recordViewModel.contents.value}".printLog()
//                    val bitmapList: List<Bitmap> = listOf(bitmap)
                } catch (e: Exception) {
                    "${e.printStackTrace()}".printLog()
                }
            } else {
                "something wrong".printLog("ActivityResult")
            }
        } else {
            "resultCode != RESULT_OK".printLog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyCheck()
        onClickUpLoadButton()

        record = arguments?.getParcelable<Record>("record") ?: Record.mockRecord

        recordViewModel.setRecord(record)
        binding.editText.setText(record.text)

        binding.galleryBtn.setOnClickListener {
            openGallery()
        }

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emptyCheck()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun emptyCheck() {
        if (binding.editText.text.toString() != "") {
            binding.blankImage.visibility = View.GONE
            binding.blank1Tv.visibility = View.GONE
            binding.toolbar.uploadBtn.isEnabled = true // 버튼 활성화
            binding.toolbar.uploadBtn.isClickable = true
            binding.toolbar.uploadBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.indicator_focus_color
                )
            )
        } else {
            binding.blankImage.visibility = View.VISIBLE
            binding.blank1Tv.visibility = View.VISIBLE
            binding.toolbar.uploadBtn.isEnabled = false // 버튼 비활성화
            binding.toolbar.uploadBtn.isClickable = false
            binding.toolbar.uploadBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.indicator_not_focus_color
                )
            )
        }
    }

    private fun onClickUpLoadButton() {
        binding.toolbar.uploadBtn.setOnClickListener {
            recordViewModel.insert(
                record.copy(
                    date = Date().date,
                    text = binding.editText.text.toString()
                )
            )
            "$record".printLog("record copy data : ")
            requireView().findNavController()
                .navigate(R.id.action_recordFragment_to_homeFragment)
        }
    }
}
