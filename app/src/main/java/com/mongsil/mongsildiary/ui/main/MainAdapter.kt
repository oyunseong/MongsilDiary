package com.mongsil.mongsildiary.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding
import com.mongsil.mongsildiary.model.Test

class MainAdapter(private val dataSet: ArrayList<List<String>>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    var datas = mutableListOf<Test>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class ViewHolder(private val binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<String>) {
            binding.timeslot.text = data[0]
        }
    }


}