package com.mongsil.mongsildiary.ui.home

import android.content.Context
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
import com.mongsil.mongsildiary.databinding.ItemViewpagerBinding
import com.mongsil.mongsildiary.model.Emotion
import com.mongsil.mongsildiary.model.TimeSlotViewPagerData

class TimeSlotAdapter(
    private val emotionList: ArrayList<TimeSlotViewPagerData>,
    private val onItemClickListener: ViewHolder.OnItemClickListener
) :
    RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val context: Context = parent.context
//        val view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, parent, false)
        val binding =
            ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = emotionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }
        holder.bind(emotionList[position])
    }

    class ViewHolder(val binding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(timeSlotViewPagerData: TimeSlotViewPagerData) {
            binding.title.text = timeSlotViewPagerData.title
        }

        interface OnItemClickListener {
            fun onClick(v: View, position: Int)
        }
    }
}