package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemViewpagerBinding
import com.mongsil.mongsildiary.model.Emoticon
import com.mongsil.mongsildiary.model.TimeSlotViewPagerData


//TODO HomeAdapter 리뷰 내용 참고
class TimeSlotAdapter(
    private val emoticonChunkList: List<List<Emoticon>>,
    private val onItemClickListener: ViewHolder.OnItemClickListener
) : RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewpagerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }
        holder.bind()
    }

    class ViewHolder(private val binding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emoticonList: List<Emoticon>) {
//            binding.title.text = timeSlotViewPagerData.title
            binding.emoticonList.apply {
                adapter = EmoticonAdapter(emoticons = emoticonList)
                layoutManager = GridLayoutManager(binding.root.context, 4)
            }
        }

        interface OnItemClickListener {
            fun onClick(v: View, position: Int)
        }
    }
}