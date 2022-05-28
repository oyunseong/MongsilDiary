package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding

/**
 * HomeFragment에서 보이는 TimeSlot RecyclerView의 Adapter
 * */

class HomeTodayAdapter(
    private val homeTodaySlotList: ArrayList<HomeTodaySlot>,
    private val onItemClickListener: ((Int) -> Unit),
) : RecyclerView.Adapter<HomeTodayAdapter.HomeTimeSlotListViewHolder>() {

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
        private val onItemClickListener: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HomeTodaySlot, position: Int) { //TODO bind 함수의 파라미터가 리스트일 필요가 없음. 고치기

            binding.root.setOnClickListener {
                onItemClickListener.invoke(position)
            }

            binding.title.text = data.title
            binding.contents.text = data.content
            binding.emoticon.setImageResource(data.image)

            if (binding.contents.text.isEmpty()) {
                binding.contents.visibility = View.GONE
                binding.emoticon.visibility = View.GONE
            }else{
                binding.plusBtn.visibility = View.GONE
            }
        }
    }
}