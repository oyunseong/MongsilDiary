package com.mongsil.mongsildiary.ui.home.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemViewpagerBinding
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.VerticalItemDecorator
import com.mongsil.mongsildiary.utils.printLog

/**
 * TodayFragment 안에 있는 ViewPager Adapter
 * */
class TodayViewPagerAdapter(
    private val emoticonChunkList: List<List<Emoticon>>,
    private val todayViewModel: TodayViewModel
) : RecyclerView.Adapter<TodayViewPagerAdapter.ViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemViewpagerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding, todayViewModel)
    }

    override fun getItemCount(): Int = emoticonChunkList.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(emoticonChunkList[position], position)
    }

    class ViewPagerViewHolder(
        private val binding: ItemViewpagerBinding,
        private val todayViewModel: TodayViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         *  @param emoticonChunkList : chunk된 이모티콘 list
         *  @param position : ViewPager2의 position 값
         *  @param
         * */
        fun bind(emoticonChunkList: List<Emoticon>, position: Int) {
            binding.emoticonList.apply {
                adapter = EmoticonAdapter(
                    emoticons = emoticonChunkList,
                    onItemClickListener = {
                        "몽실이 클릭".printLog("$it 번째 몽실")
                        todayViewModel.selectEmoticon(emoticonChunkList[it])
                    },
                    todayViewModel = todayViewModel
                )

                addItemDecoration(HorizontalItemDecorator(8))
                addItemDecoration(VerticalItemDecorator(8))
                layoutManager = GridLayoutManager(binding.root.context, 4)
            }
        }
    }

    fun setSelectedEmoticon(emoticon: Emoticon) {

    }

}