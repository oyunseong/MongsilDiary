package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding
import com.mongsil.mongsildiary.databinding.ItemTimeslotListBinding
import com.mongsil.mongsildiary.model.Emotion

class TimeSlotAdapter(
    private val dataSet: ArrayList<List<Emotion>>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTimeslotListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val gridLayoutManager = GridLayoutManager(holder.itemView.context,3)

        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }

        holder.bind(dataSet[position])
    }

    class ViewHolder(private val binding: ItemTimeslotListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<Emotion>) {
            binding.image.setImageResource(R.drawable.ic_emoticon_01)
            binding.text.text = "몽실이"
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
}