package com.mongsil.mongsildiary.ui.home

import HorizontalItemDecorator
import VerticalItemDecorator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.model.RecordViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val recordViewModel: RecordViewModel by activityViewModels()

    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val homeAdapter = HomeAdapter(dataSet, object : HomeAdapter.OnItemClickListener {
        override fun onClick(v: View, position: Int) {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_timeSlotFragment)
            showToast("${position}번째 item")
        }
    })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addData()
        initRecycler()
        setRecordOption()
    }

    private fun setRecordOption() {
        binding.addBtn.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_homeFragment_to_recordFragment)
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
            showToast("삭제버튼 클릭")
        }
        binding.editBtn.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_homeFragment_to_recordFragment)
        }
    }

    private fun initRecycler() {
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = homeAdapter
        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))
    }

    private fun addData() {
        for (i in 0..99) {
            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }

}