package com.mongsil.mongsildiary.ui.main

import HorizontalItemDecorator
import VerticalItemDecorator
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityMainBinding
import com.mongsil.mongsildiary.model.Test

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    private lateinit var mainAdapter: MainAdapter
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addData()
        initRecycler()
        settingFab()
    }

    private fun initRecycler() {
        binding.recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = MainAdapter(dataSet)
        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))
//        binding.recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
    }

    private fun settingFab() {
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)

        binding.fab.setOnClickListener {
            if (isOpen) {

                binding.settingFab.startAnimation(fabClose)
                binding.cheeringFab.startAnimation(fabClose)
                binding.calendarFab.startAnimation(fabClose)
                binding.settingTv.startAnimation(fabClose)
                binding.cheeringTv.startAnimation(fabClose)
                binding.calendarTv.startAnimation(fabClose)
                binding.fab.startAnimation(fabRClockwise)

                isOpen = false

            } else {
                binding.settingFab.startAnimation(fabOpen)
                binding.cheeringFab.startAnimation(fabOpen)
                binding.calendarFab.startAnimation(fabOpen)
                binding.settingTv.startAnimation(fabOpen)
                binding.cheeringTv.startAnimation(fabOpen)
                binding.calendarTv.startAnimation(fabOpen)
                binding.fab.startAnimation(fabRAntiClockwise)

                binding.settingFab.isClickable
                binding.cheeringFab.isClickable
                binding.calendarFab.isClickable

                isOpen = true

                binding.settingFab.setOnClickListener {
                    Toast.makeText(this, "setting 버튼을 클릭하셨습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addData() {
        for (i in 0..99) {
            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }
}