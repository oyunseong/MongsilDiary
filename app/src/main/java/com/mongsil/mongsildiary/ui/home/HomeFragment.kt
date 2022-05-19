package com.mongsil.mongsildiary.ui.home

import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.VerticalItemDecorator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.model.RecordViewModel
import com.mongsil.mongsildiary.utils.showToast

class HomeFragment : Fragment(){

    private val recordViewModel: RecordViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val homeAdapter = HomeAdapter(dataSet, object : HomeAdapter.OnItemClickListener {
        override fun onClick(v: View, position: Int) {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_timeSlotFragment)
            requireContext().showToast("${position}번째 item")
        }
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
        initRecycler()
        setRecordOption()
    }

    private fun setRecordOption() {
        binding.addBtn.setOnClickListener {
            //TODO view!! -> requireView() 함수로 대체가능. 해당 함수 내부 한 번 설펴보기.
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
            requireContext().showToast("삭제버튼 클릭")
        }
        binding.editBtn.setOnClickListener {
            //TODO view!! -> requireView() 함수로 대체가능. 해당 함수 내부 한 번 설펴보기.
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