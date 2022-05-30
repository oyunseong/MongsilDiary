package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.model.RecordViewModel
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.VerticalItemDecorator
import com.mongsil.mongsildiary.utils.showToast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {

    private val recordViewModel: RecordViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeTodaySlotList: ArrayList<HomeTodaySlot> =
        arrayListOf()
    private val homeTimeSlotAdapter = HomeTodayAdapter(homeTodaySlotList, onItemClickListener = {
        val todayTitle = homeTodaySlotList[it].title
        val todayContents = homeTodaySlotList[it].content
        setFragmentResult("todayTitle", bundleOf("titleBundleKey" to todayTitle))
        setFragmentResult("todayContents", bundleOf("contentsBundleKey" to todayContents))
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_timeSlotFragment)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
        setTodayRecycler()
        setRecordOption()
        setCurrentDate()
    }

    private fun setCurrentDate() {
        val currentDate = System.currentTimeMillis()
        val date = Date(currentDate)
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        binding.date.text = sdf.format(date)
    }

    private fun setRecordOption() {
        binding.addBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_homeFragment_to_recordFragment)
        }
        binding.deleteBtn.visibility = View.GONE
        binding.editBtn.visibility = View.GONE

        recordViewModel.contents.observe(viewLifecycleOwner) {

            binding.recordContents.text = recordViewModel.contents.value
            if (it.isNotEmpty()) {
                binding.deleteBtn.visibility = View.VISIBLE
                binding.editBtn.visibility = View.VISIBLE
                binding.addBtn.visibility = View.GONE
            } else {
                binding.deleteBtn.visibility = View.GONE
                binding.editBtn.visibility = View.GONE
                binding.addBtn.visibility = View.VISIBLE
            }
        }

        binding.deleteBtn.setOnClickListener {
            recordViewModel.setContents("")
            requireContext().showToast("삭제버튼 클릭")
        }
        binding.editBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_homeFragment_to_recordFragment)
        }
    }

    private fun setTodayRecycler() {
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.recycler.adapter = homeTimeSlotAdapter

        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))
    }

    private fun addData() {
        if (homeTodaySlotList.isEmpty()) {
            homeTodaySlotList.add(HomeTodaySlot("아침", R.drawable.ic_emoticon_01, "asd"))
            homeTodaySlotList.add(HomeTodaySlot("점심", R.drawable.ic_emoticon_02, "asd"))
            homeTodaySlotList.add(HomeTodaySlot("저녁", R.drawable.ic_emoticon_03, "asd"))
            homeTodaySlotList.add(HomeTodaySlot("하루 끝", R.drawable.ic_emoticon_04, ""))
        }
    }

}