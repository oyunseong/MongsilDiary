package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.ui.RecordViewModel
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.VerticalItemDecorator
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.showToast


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val recordViewModel by viewModels<RecordViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>(factoryProducer = {
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        }
    })

    // TODO
    //  slot을 번들로 전달 -> 전달받은 번들을 멤버변수로 선언 -> slot data를 Slot객체를 생성해서 insert 함수 호출
    private val homeTimeSlotAdapter = HomeTodayAdapter(onItemClickListener = {
        val bundle = bundleOf("slot" to it)
        requireView().findNavController().navigate(R.id.action_homeFragment_to_todayFragment, bundle)
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

        setTodayRecycler()
        setRecordOption()
        setCurrentDate()

        homeViewModel.slotData.observe(viewLifecycleOwner, object : Observer<List<Slot>> {
            override fun onChanged(t: List<Slot>?) {
                "$t".printLog("List<Slot>'s data")
                homeTimeSlotAdapter.setData(t ?: emptyList())
            }
        })
    }

    private fun setCurrentDate() {
        binding.date.text = Date().date_dot
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
}