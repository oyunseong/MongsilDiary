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
import com.mongsil.mongsildiary.model.Emotion

class TimeSlotAdapter(
    val context: Context,
    private val dataSet: ArrayList<List<Emotion>>,
    private val onItemClickListener: ViewHolder.OnItemClickListener
) :
    RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {
    val indexList = Array(dataSet.size) { false }   // 리스트를 전체 false로 초기화
    var index = 0 // 선택된 item's index

    init {
        if (dataSet.isNotEmpty())
            indexList[0] = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTimeslotListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asset = context.resources.getDrawable(
            if (indexList[position]) R.drawable.fill_circle
            else R.drawable.stoke_circle
        )

        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }

        holder.bind(dataSet[position])
//        holder.bind(asset)
    }

    class ViewHolder(private val binding: ItemTimeslotListBinding) :
        RecyclerView.ViewHolder(binding.root) {
//            val dotsView : View = bindi

        fun bind(data: List<Emotion>) {
            binding.image.setImageResource(R.drawable.ic_emoticon_01)
            binding.text.text = "몽실이"
//        }
        }

        interface OnItemClickListener {
            fun onClick(v: View, position: Int)
        }

    }

    fun setItemIndex(position: Int) {
        indexList[index] = false
        indexList[position] = true
        index = position

        notifyDataSetChanged()
    }
}