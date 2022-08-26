package com.mongsil.mongsildiary.ui.calendar

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.ThisMonthMongsilBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.utils.printLog
import java.util.*
import kotlin.collections.HashMap

class ThisMonthMongsilAdapter() :
    RecyclerView.Adapter<ThisMonthMongsilAdapter.ThisMonthMongsilViewHolder>() {

    private var thisMonthMongsilList = emptyList<Pair<Int, Int>>()
    private var hashmap = HashMap<Int, Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThisMonthMongsilViewHolder {
        val binding =
            ThisMonthMongsilBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ThisMonthMongsilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThisMonthMongsilViewHolder, position: Int) {
        holder.bind(thisMonthMongsilList[position])
    }

    override fun getItemCount(): Int {
        return thisMonthMongsilList.size
    }

    class ThisMonthMongsilViewHolder(private val binding: ThisMonthMongsilBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: Pair<Int, Int>) {
            when (list.first) {
                0 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_01)
                1 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_02)
                2 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_03)
                3 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_04)
                4 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_05)
                5 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_06)
                6 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_07)
                7 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_08)
                8 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_09)
                9 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_10)
                10 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_11)
                11 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_12)
                12 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_13)
                13 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_14)
                14 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_15)
                15 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_16)
                16 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_17)
                17 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_18)
                18 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_19)
                19 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_20)
                20 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_21)
                21 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_22)
                22 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_23)
                23 -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_24)
                else -> binding.emoticon.setImageResource(R.drawable.ic_emoticon_01)
            }
            binding.progress.progress = list.second
            binding.count.text = list.second.toString()
        }

    }

    /**
     * @param list : Slot List
     * Key : Emoticon의 id
     * Value : Emoticon의 개수
     * 1. Slot이 담긴 List를 받아 크기만큼 for문 돈다
     * 2. key값으로 이모티콘id를 받고 map에 중복된 id가 없다면 (key, 1) 매핑
     * 3. 중복된 키가 존재한다면 (key, value +1)
     * 4. map을 list로 변환하고 value 값을 기준으로 내림차순 수행하고 list에 담는다
     * TODO 출력되는 달의 emoticon만 출력하도록 수정
     * */
    fun setMongsilList(list: List<Slot>) {
        list.forEach {
            if (hashmap.containsKey(it.emoticon.id)) {
                hashmap[it.emoticon.id] = hashmap[it.emoticon.id]!!.toInt() + 1
            } else {
                hashmap[it.emoticon.id] = 1
            }
        }
        thisMonthMongsilList = hashmap.toList().sortedByDescending { it.second }
        notifyDataSetChanged()
    }
}