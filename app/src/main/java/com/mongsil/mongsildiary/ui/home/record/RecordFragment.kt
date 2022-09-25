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
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.repeatOnStarted
import com.mongsil.mongsildiary.utils.showToast
import java.io.ByteArrayOutputStream

class RecordFragment : BaseFragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var record: Record
    private lateinit var bitmap: Bitmap
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
                    bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        currentImageUrl
                    )
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
        if (binding.editText.text.toString().isEmpty()) {
            addEmptyCheckImage()
        } else if (record.image != null) {
            removeEmptyCheckImage()
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
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private fun onClickUpLoadButton() {
        binding.toolbar.uploadBtn.setOnClickListener {
            val date = Date().convertCalendarDayToLong(mainViewModel.date.value!!)
            mainViewModel.insertRecord(
                record.copy(
                    date = date,
                    text = binding.editText.text.toString(),
                    image = getImageUri(requireContext(), binding.firstImageView.drawToBitmap())
                ), requireContext()
            )

            findNavController().popBackStack()
        }
    }

    private fun handleEvent(event: MainViewModel.Event) = when (event) {
        is MainViewModel.Event.ShowProgress -> mainViewModel.showProgress()
        is MainViewModel.Event.HideProgress -> mainViewModel.hideProgress()
    }
}
