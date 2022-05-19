package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemTimeslotListBinding
import com.mongsil.mongsildiary.model.Emoticon
import com.mongsil.mongsildiary.ui.home.timeSlot.TimeSlotAdapter

class EmoticonAdapter(
    val emoticons: List<Emoticon>,
    private val onItemClickListener: ViewHolder.OnItemClickListener
) : RecyclerView.Adapter<EmoticonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTimeslotListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }

        holder.bind(emoticons[position])

    }

    override fun getItemCount(): Int = emoticons.size

    class ViewHolder(private val binding: ItemTimeslotListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(emoticonList: Emoticon) {
            binding.emoticonImage.setImageResource(emoticonList.image)
            binding.emoticonText.text = emoticonList.name
        }

        interface OnItemClickListener {
            fun onClick(v: View, position: Int) {
            }
        }
    }


}