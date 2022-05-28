package com.mongsil.mongsildiary.ui.home.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemTimeslotListBinding

/**
 * ViewPager2안에 있는 이모티콘 item
 * */
class EmoticonAdapter(
    val emoticons: List<TodayEmoticon>,
    private val onItemClickListener: ((Int) -> Unit),
//    private val emoticonViewModel: EmoticonViewModel,
) : RecyclerView.Adapter<EmoticonAdapter.EmoticonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmoticonViewHolder {
        val binding = ItemTimeslotListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EmoticonViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: EmoticonViewHolder, position: Int) {
        holder.bind(emoticons[position],position)
    }

    override fun getItemCount(): Int = emoticons.size

    class EmoticonViewHolder(
        private val binding: ItemTimeslotListBinding,
        private val onItemClickListener: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emoticon: TodayEmoticon,position: Int) {
            binding.emoticonImage.setImageResource(emoticon.emoticon.image)
            binding.emoticonText.text = emoticon.emoticon.name

            binding.root.setOnClickListener {
                onItemClickListener.invoke(position)
            }

        }

    }


}