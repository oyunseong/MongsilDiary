package com.mongsil.mongsildiary.ui.main

import HorizontalItemDecorator
import VerticalItemDecorator
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityMainBinding
import com.mongsil.mongsildiary.model.Test

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it)}) {
    private lateinit var mainAdapter: MainAdapter
    private val dataSet: ArrayList<List<String>> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        addData()
        initRecycler()
    }

    private fun initRecycler(){
        binding.recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recycler.adapter = MainAdapter(dataSet)
        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))
//        binding.recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
    }

    private fun addData() {
        for (i in 0..99) {
            dataSet.add(listOf("$i th main", "$i th sub"))
        }
    }
}