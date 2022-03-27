package com.mongsil.mongsildiary.ui.home

import HorizontalItemDecorator
import VerticalItemDecorator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.HomeAdapter
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.model.RecordViewModel
import com.mongsil.mongsildiary.utils.log

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
//    private val recordViewModel: RecordViewModel by lazy {
//        ViewModelProvider(this)[RecordViewModel::class.java]
//    }
    val recordViewModel : RecordViewModel by activityViewModels()

    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val mainAdapter = HomeAdapter(dataSet, object : HomeAdapter.OnItemClickListener {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
        initRecycler()



        binding.addBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_recordFragment)
        }
        "${recordViewModel.contents.value}".log()
        recordViewModel.contents.observe(viewLifecycleOwner) {
            binding.recordContents.text = recordViewModel.contents.value

        }
    }

    private fun initRecycler() {
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = mainAdapter
        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))
    }

    private fun addData() {
        for (i in 0..99) {
            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }

}