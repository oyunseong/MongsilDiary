package com.mongsil.mongsildiary.ui.home

import HorizontalItemDecorator
import VerticalItemDecorator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentTimeslotBinding
import com.mongsil.mongsildiary.model.Emotion
import kotlin.collections.ArrayList

class TimeSlotFragment : BaseFragment<FragmentTimeslotBinding>() {
    private val dataSet: ArrayList<List<Emotion>> = arrayListOf()
    private val timeSlotAdapter =
            TimeSlotAdapter(dataSet, object :
                TimeSlotAdapter.ViewHolder.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                showToast("${position}번째 item")
            }
        })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimeslotBinding {
        return FragmentTimeslotBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabViewModel.setFabState(false)
        initRecycler()

//        binding.viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.viewpager2.adapter = timeSlotAdapter

        binding.confirmBtn.isEnabled = false    // 버튼 비활성화
        binding.confirmBtn.isClickable = false

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editText.length() == 0) {   // 2자리 미만이면
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
            if (it.isEnabled) {
                view?.findNavController().navigate(R.id.action_timeSlotFragment_to_homeFragment)
                showToast("클릭 되었습니다.")
            } else {
                showToast("error!!")
            }
        }

        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun addData() {
        for (i in 0..99) {
            dataSet.add(listOf(Emotion(R.drawable.ic_emoticon_01, "몽실이")))
//            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }

    private fun initRecycler() {
        binding.recycler.layoutManager =
            GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false)
//            LinearLayoutManager(requireContext(), GridLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = timeSlotAdapter
        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.onFlingListener = snapHelper

        binding.recycler.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                timeSlotAdapter.setItemIndex(
                    (recyclerView.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                )
            }
        })

//        rv_main_index.addOnItemTouchListener(
//            RecyclerItemClickListener(applicationContext, rv_main_index,
//                object : RecyclerItemClickListener.OnItemClickListener {
//                    override fun onItemClick(view: View, position: Int) {
//                        rv_main_card.smoothScrollToPosition(position)
//                    }
//                })
//        )
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