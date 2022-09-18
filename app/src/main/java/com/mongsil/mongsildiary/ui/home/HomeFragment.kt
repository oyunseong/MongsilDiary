package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.MainViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding
import com.mongsil.mongsildiary.domain.Saying
import com.mongsil.mongsildiary.server.GithubAPI
import com.mongsil.mongsildiary.server.RetrofitClient
import com.mongsil.mongsildiary.utils.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recordBundle: Bundle
    lateinit var retrofit: Retrofit
    lateinit var githubAPI: GithubAPI

    private val homeViewModel by viewModels<HomeViewModel>(factoryProducer = {
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        }
    })

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val homeTimeSlotAdapter = HomeTodayAdapter(onItemClickListener = {
        val bundle = bundleOf("slot" to it)
        findNavController().navigate(R.id.action_homeFragment_to_todayFragment, bundle)
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
        setToolbar()
        initServer()
        setTodayRecycler()
        setRecordOption()

        mainViewModel.date.observe(viewLifecycleOwner) {
            homeViewModel.getSlotData(it)
            setCurrentDate(it)
        }

        homeViewModel.slotData.observe(viewLifecycleOwner) {
            homeTimeSlotAdapter.setData(it)
        }

        homeViewModel.saying.observe(viewLifecycleOwner) {
            val index: Int = if (it.sayingList.isEmpty()) {
                0
            } else {
                (1..it.sayingList.size).random()
            }
            binding.saying.text = it.sayingList[index]
        }
    }

    private fun setCurrentDate(date: CalendarDay) {
        if (date == CalendarDay.today()) {
            binding.date.visibility = View.VISIBLE
            binding.date.text = Date().plusDotCalendarDay(date)
            binding.mainTitle.setText(R.string.main_title)
            binding.toolbar.toolbar.visibility = View.GONE
        } else {
            binding.mainTitle.text = "${Date().otherDayText(date)}\n기록이에요"
            binding.date.visibility = View.GONE
            binding.toolbar.toolbar.visibility = View.VISIBLE
        }

    }

    private fun setRecordOption() {
        mainViewModel.recordData.observe(viewLifecycleOwner) {
            recordBundle = bundleOf("record" to it)
            if (it.text.isNotEmpty() || it.image != null) {
                binding.deleteBtn.visibility = View.VISIBLE
                binding.editBtn.visibility = View.VISIBLE
                binding.addBtn.visibility = View.GONE
            } else {
                binding.deleteBtn.visibility = View.GONE
                binding.editBtn.visibility = View.GONE
                binding.addBtn.visibility = View.VISIBLE
            }
            binding.recordContents.text = it.text
            binding.recordImage.setImageBitmap(it.image)
        }

        binding.deleteBtn.setOnClickListener {
            mainViewModel.deleteRecord()
        }

        binding.addBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeFragment_to_recordFragment, recordBundle)
        }

        binding.editBtn.setOnClickListener {
            findNavController()
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

    private fun setToolbar() {
        binding.toolbar.apply {
            toolbar.visibility = View.GONE
            title.visibility = View.GONE
            uploadBtn.visibility = View.GONE
        }

        binding.toolbar.backBtn.setOnClickListener {
            mainViewModel.setDate(CalendarDay.today())
        }
    }

    private fun initServer() {
        retrofit = RetrofitClient.getInstance()
        githubAPI = retrofit.create(GithubAPI::class.java)

        Runnable {
            githubAPI.getSayingList().enqueue(object : retrofit2.Callback<Saying> {
                override fun onResponse(call: Call<Saying>, response: Response<Saying>) {
                    if (response.isSuccessful) {
                        val saying: Saying? = response.body()
                        if (saying != null) {
                            homeViewModel.setSaying(saying)
                        } else {
                            "saying is null".printLog("githubAPI")
                            return
                        }
                    }
                }

                override fun onFailure(call: Call<Saying>, t: Throwable) {
                    "onFailure message : " + t.message.toString().printLog("initServer")
                    requireActivity().showToast(t.message + "통신실패")
                    call.cancel()
                }
            })
        }.run()
    }
}