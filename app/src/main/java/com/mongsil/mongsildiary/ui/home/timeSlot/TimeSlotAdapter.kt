package com.mongsil.mongsildiary.ui.home.timeSlot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemViewpagerBinding
import com.mongsil.mongsildiary.ui.home.EmoticonAdapter
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.VerticalItemDecorator
import com.mongsil.mongsildiary.utils.showToast


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

    override fun getItemCount(): Int = emoticonChunkList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }
        holder.bind(emoticonChunkList[position])
    }

    class ViewHolder(private val binding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emoticonList: List<Emoticon>) {
            binding.emoticonList.apply {
                adapter = EmoticonAdapter(
                    emoticons = emoticonList,
                    object : EmoticonAdapter.ViewHolder.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            super.onClick(v, position)
                            context.showToast("$position 번째 item")
                        }
                    })

                addItemDecoration(VerticalItemDecorator(8))
                addItemDecoration(HorizontalItemDecorator(8))
                layoutManager = GridLayoutManager(binding.root.context, 4)
            }
        }

        interface OnItemClickListener {
            fun onClick(v: View, position: Int)
        }
    }

}