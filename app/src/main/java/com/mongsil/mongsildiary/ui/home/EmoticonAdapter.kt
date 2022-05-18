package com.mongsil.mongsildiary.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.model.Emoticon

class EmoticonAdapter(emoticons: List<Emoticon>) : RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TimeSlotAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class EmoticonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(emoticon: Emoticon) {

        }
    }
}