package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.MainActivity
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.ui.RecordViewModel
import com.mongsil.mongsildiary.ui.home.today.Emoticon
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.VerticalItemDecorator
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.showToast
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val todayViewModel by viewModels<TodayViewModel>()
    private val recordViewModel by viewModels<RecordViewModel>()

    private val repo: DiaryRepository by lazy {
        DiaryRepository(AppDatabase.getInstance(requireContext()).diaryDao())
    }

    private var homeTodaySlotList: List<Slot> =
        arrayListOf()
    private val homeTimeSlotAdapter = HomeTodayAdapter(homeTodaySlotList, onItemClickListener = {
        val todayTitle = homeTodaySlotList[it].timeSlot
        val todayContents = homeTodaySlotList[it].text
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        repo = DiaryRepository(AppDatabase.getInstance(requireContext()).diaryDao())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        addData()
        todayViewModel.contents.observe(viewLifecycleOwner){
            binding.recycler.adapter as HomeTodayAdapter
        }
        setTodayRecycler()
        setRecordOption()
        setCurrentDate()

        binding.cheerUpTv.setOnClickListener {
            lifecycleScope.launch {
                "클릭".printLog("HomeFragment")
                repo.insertSlot(Slot.mockSlot)
                val r = repo.getSlotsByDate(100).toString().printLog("HomeFragment")
                homeTodaySlotList = repo.getSlotsByDate(100)
            }
        }

//        val db = Room.databaseBuilder(
//            requireContext(),
//            AppDatabase::class.java, "MongsilDatabase"
//        ).build()

//        val dao = db.diaryDao()
//        val models: List<SlotEntity> = dao.getALLInfo()
//        binding.cheerUpTv.setOnClickListener {
//            "$models".printLog("HomeFragment")
//        }
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

//    private fun addData() {
//        if (homeTodaySlotList.isEmpty()) {
//            homeTodaySlotList.add(
//                Slot(
//                    100, "아침",
//                    TimeSlot.Morning, Emoticon(
//                        id = 2,
//                        image = R.drawable.ic_emoticon_03,
//                        name = "노랑"
//                    )
//                )
//            )
//            homeTodaySlotList.add(
//                Slot(
//                    20220629, "점심",
//                    TimeSlot.Launch, Emoticon(
//                        id = 2,
//                        image = R.drawable.ic_emoticon_03,
//                        name = "노랑"
//                    )
//                )
//            )
//            homeTodaySlotList.add(
//                Slot(
//                    20220629, "저녁",
//                    TimeSlot.Dinner, Emoticon(
//                        id = 2,
//                        image = R.drawable.ic_emoticon_03,
//                        name = "노랑"
//                    )
//                )
//            )
//            homeTodaySlotList.add(
//                Slot(
//                    20220629, "하루끝",
//                    TimeSlot.EndOfTheDay, Emoticon(
//                        id = 2,
//                        image = R.drawable.ic_emoticon_03,
//                        name = "노랑"
//                    )
//                )
//            )
//        }
//    }

}