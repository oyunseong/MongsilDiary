package com.mongsil.mongsildiary.ui.home.record

import android.app.Activity.RESULT_OK
import android.content.Context
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mongsil.mongsildiary.MainViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentRecordBinding
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.utils.*
import com.mongsil.mongsildiary.utils.Date
import java.io.ByteArrayOutputStream
import java.util.*

class RecordFragment : BaseFragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var record: Record
    private val recordViewModel by viewModels<RecordViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            recordViewModel.setRecord(record.copy(image = result.data?.data))
        }

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
            try {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                getContent.launch(intent)
            } catch (e: Exception) {
                requireContext().showToast("갤러리를 찾을 수 없습니다.")
            }
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
                    recordViewModel.setRecord(record.copy(image = currentImageUrl))
                } catch (e: Exception) {
                    e.printStackTrace()
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
        onClickUpLoadButton()
        record = arguments?.getParcelable<Record>("record") ?: Record.mockRecord
        recordViewModel.setRecord(record)
        binding.editText.setText(record.text)


        // blank 이미지, 텍스트 클릭시 키보드 보이기
        binding.blankImage.setOnClickListener {
            binding.editText.apply {
                post {
                    this.isFocusableInTouchMode = true
                    this.requestFocus()
                    it.showKeyboard(binding.editText)
                }
            }
        }

        binding.blank1Tv.setOnClickListener {
            binding.editText.apply {
                post {
                    this.isFocusableInTouchMode = true
                    this.requestFocus()
                    it.showKeyboard(binding.editText)
                }
            }
        }

        recordViewModel.contents.observe(viewLifecycleOwner) {
            binding.firstImageView.setImageURI(it.image)
            emptyCheck()
        }

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

        repeatOnStarted {
            mainViewModel.showProgressEvent.collect { event -> handleEvent(event) }
        }
    }

    fun emptyCheck() {
        if (binding.editText.text.toString().isEmpty() && binding.firstImageView.drawable == null) {
            addEmptyCheckImage()
        } else {
            removeEmptyCheckImage()
        }
    }

    private fun addEmptyCheckImage() {
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

    private fun removeEmptyCheckImage() {
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
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        return try {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                context.contentResolver,
                inImage,
                "IMG_" + Calendar.getInstance().timeInMillis,
                null
            )
            Uri.parse(path)
        } catch (e: Exception) {
            requireContext().showToast("Uri를 찾을 수 없습니다.")
            Uri.EMPTY
        }
    }

    private fun onClickUpLoadButton() {
        binding.toolbar.uploadBtn.setOnClickListener {
            val date = Date().convertCalendarDayToLong(mainViewModel.date.value!!)
            if (binding.firstImageView.drawable == null) {
                mainViewModel.insertRecord(
                    record.copy(
                        date = date,
                        text = binding.editText.text.toString(),
                        image = null
                    ), requireContext()
                )
            } else {
                mainViewModel.insertRecord(
                    record.copy(
                        date = date,
                        text = binding.editText.text.toString(),
                        image = getImageUri(
                            requireContext(),
                            binding.firstImageView.drawToBitmap()
                        )
                    ), requireContext()
                )
            }
            findNavController().popBackStack()
            it.hideKeyboard()
        }
    }

    private fun handleEvent(event: MainViewModel.Event) = when (event) {
        is MainViewModel.Event.ShowProgress -> mainViewModel.showProgress()
        is MainViewModel.Event.HideProgress -> mainViewModel.hideProgress()
    }

}
