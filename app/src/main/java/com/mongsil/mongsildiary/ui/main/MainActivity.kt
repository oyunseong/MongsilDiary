package com.mongsil.mongsildiary.ui.main

import HorizontalItemDecorator
import VerticalItemDecorator
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.TranslucentActivity
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    private lateinit var mainAdapter: MainAdapter
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private var isOpen: Boolean = false
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var fabRClockwise: Animation
    private lateinit var fabRAntiClockwise: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)
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
        binding.fab.setOnClickListener {
            if (isOpen) {
                fabClosed()

            } else {
                binding.settingFab.startAnimation(fabOpen)
                binding.cheeringFab.startAnimation(fabOpen)
                binding.calendarFab.startAnimation(fabOpen)
                binding.settingTv.startAnimation(fabOpen)
                binding.cheeringTv.startAnimation(fabOpen)
                binding.calendarTv.startAnimation(fabOpen)
                binding.fab.startAnimation(fabRAntiClockwise)

                binding.shadowView.visibility = View.VISIBLE
                binding.shadowView.bringToFront()
                binding.settingTv.bringToFront()
                binding.cheeringTv.bringToFront()
                binding.calendarTv.bringToFront()

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

    private fun fabClosed() {
        binding.settingFab.startAnimation(fabClose)
        binding.cheeringFab.startAnimation(fabClose)
        binding.calendarFab.startAnimation(fabClose)
        binding.settingTv.startAnimation(fabClose)
        binding.cheeringTv.startAnimation(fabClose)
        binding.calendarTv.startAnimation(fabClose)
        binding.fab.startAnimation(fabRClockwise)

        binding.shadowView.visibility = View.GONE
        isOpen = false
    }

    private fun addData() {
        for (i in 0..99) {
            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }

    var backKeyPressedTime = 0L
    override fun onBackPressed() {
//        super.onBackPressed()

        if (binding.shadowView.visibility == View.VISIBLE) {
            binding.shadowView.visibility = View.GONE
            fabClosed()
        }
        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
            backKeyPressedTime = System.currentTimeMillis()
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
            finish()
        }
    }
}

