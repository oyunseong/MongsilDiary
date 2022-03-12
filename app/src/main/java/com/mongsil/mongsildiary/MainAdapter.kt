package com.mongsil.mongsildiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding

class MainAdapter(private val dataSet: ArrayList<List<String>>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it,position)
        }

        holder.bind(dataSet[position])


    }

    class ViewHolder(private val binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<String>) {
            binding.timeslot.text = data[0]
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }



}