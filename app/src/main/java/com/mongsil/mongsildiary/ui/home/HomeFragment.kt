package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.MainViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.utils.*


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recordBundle: Bundle

    private val homeViewModel by viewModels<HomeViewModel>(factoryProducer = {
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        }
    })

    //    private val mainViewModel by viewModels<MainViewModel>(ownerProducer = {
//        requireActivity()
//    })

    //TODO Activity ViewModel
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val homeTimeSlotAdapter = HomeTodayAdapter(onItemClickListener = {
        val bundle = bundleOf("slot" to it)
        requireView().findNavController()
            .navigate(R.id.action_homeFragment_to_todayFragment, bundle)
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
        mainViewModel.getRecordData()
        setTodayRecycler()
        setRecordOption()
        setCurrentDate()

        homeViewModel.slotData.observe(viewLifecycleOwner, object : Observer<List<Slot>> {
            override fun onChanged(t: List<Slot>?) {
                homeTimeSlotAdapter.setData(t ?: emptyList())
            }
        })
    }

    private fun setCurrentDate() {
        binding.date.text = Date().date_dot
    }

    private fun setRecordOption() {
        mainViewModel.recordData.observe(viewLifecycleOwner) {
            recordBundle = bundleOf("record" to it)

            "$it".printLog("print record")
            if (it.text == "") {
                binding.deleteBtn.visibility = View.GONE
                binding.editBtn.visibility = View.GONE
                binding.addBtn.visibility = View.VISIBLE
            } else {
                binding.deleteBtn.visibility = View.VISIBLE
                binding.editBtn.visibility = View.VISIBLE
                binding.addBtn.visibility = View.GONE
            }
            binding.recordContents.text = it.text
        }

        binding.deleteBtn.setOnClickListener {
            mainViewModel.deleteRecord()
            requireContext().showToast("삭제버튼 클릭")
        }

        binding.addBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(R.id.action_homeFragment_to_recordFragment, recordBundle)
        }

        binding.editBtn.setOnClickListener {
            requireView().findNavController()
                .navigate(R.id.action_homeFragment_to_recordFragment, recordBundle)
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