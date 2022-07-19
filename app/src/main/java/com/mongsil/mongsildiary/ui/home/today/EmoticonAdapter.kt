package com.mongsil.mongsildiary.ui.home.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemTimeslotListBinding
import com.mongsil.mongsildiary.utils.findCurrentFragment
import com.mongsil.mongsildiary.utils.printLog

/**
 * ViewPager2안에 있는 이모티콘 item
 * */
class EmoticonAdapter(
    val emoticons: List<TodayEmoticon>,
    private val onItemClickListener: ((Int) -> Unit),
    private val todayViewModel: TodayViewModel
) : RecyclerView.Adapter<EmoticonAdapter.EmoticonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmoticonViewHolder {
        val binding = ItemTimeslotListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EmoticonViewHolder(binding, onItemClickListener, todayViewModel)
    }

    override fun onBindViewHolder(holder: EmoticonViewHolder, position: Int) {
        holder.bind(emoticons[position], position)
    }

    override fun getItemCount(): Int = emoticons.size

    class EmoticonViewHolder(
        private val binding: ItemTimeslotListBinding,
        private val onItemClickListener: (Int) -> Unit,
        private val todayViewModel: TodayViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        private var lifecycleOwner: LifecycleOwner? = null

        init {
            lifecycleOwner = findCurrentFragment(itemView)
        }

        fun bind(emoticon: TodayEmoticon, position: Int) {

            binding.emoticonImage.setImageResource(emoticon.emoticon.image)
            binding.emoticonText.text = emoticon.emoticon.name

            binding.root.setOnClickListener {
                onItemClickListener.invoke(position)
            }

            lifecycleOwner?.let {
                todayViewModel.selectedEmotionState.observe(it) { selectedEmoticon ->
                    binding.rootLayout.isSelected = emoticon.emoticon == selectedEmoticon
                    "${binding.rootLayout.isSelected}".printLog()
                }
            }
        }

    }


}