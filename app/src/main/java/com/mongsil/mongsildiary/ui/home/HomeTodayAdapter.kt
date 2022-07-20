package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.utils.converterTimeSlot

/**
 * HomeFragment에서 보이는 TimeSlot RecyclerView의 Adapter
 * */

class HomeTodayAdapter(
    private val onItemClickListener: (Slot) -> Unit
) : RecyclerView.Adapter<HomeTodayAdapter.HomeTimeSlotListViewHolder>() {

    private var homeTodaySlotList: List<Slot> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTimeSlotListViewHolder {
        val binding = ItemDiaryListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeTimeSlotListViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount(): Int = homeTodaySlotList.size

    override fun onBindViewHolder(holder: HomeTimeSlotListViewHolder, position: Int) {
        holder.bind(homeTodaySlotList[position], position)
    }

    class HomeTimeSlotListViewHolder(
        private val binding: ItemDiaryListBinding,
        private val onItemClickListener: (Slot) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(slot: Slot, position: Int) {

            binding.root.setOnClickListener {
                onItemClickListener.invoke(slot)
            }

            binding.title.text = slot.timeSlot.converterTimeSlot()
            binding.contents.text = slot.text
            binding.emoticon.setImageResource(slot.emoticon.image)

            if (binding.contents.text.isEmpty()) {
                binding.contents.visibility = View.GONE
                binding.emoticon.visibility = View.GONE
            } else {
                binding.plusBtn.visibility = View.GONE
            }
        }
    }

    fun setData(list: List<Slot>) {
        if (homeTodaySlotList == list) {
            return
        }
        homeTodaySlotList = list
        notifyDataSetChanged()
    }
}