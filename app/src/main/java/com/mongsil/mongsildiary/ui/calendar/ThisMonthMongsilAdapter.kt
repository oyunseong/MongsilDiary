package com.mongsil.mongsildiary.ui.calendar

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.ThisMonthMongsilBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.ui.home.today.DataProvider
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.setTextColorRes
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
        holder.bind(thisMonthMongsilList[position], thisMonthMongsilList[0].second)
    }

    override fun getItemCount(): Int {
        return thisMonthMongsilList.size
    }

    class ThisMonthMongsilViewHolder(private val binding: ThisMonthMongsilBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: Pair<Int, Int>, max: Int) {

            /**
             * 제일 높은 값을 최고값으로 설정
             * 제일 높은 값을 기준으로 모든 값을 나눈다
             * ex) 가장 높은값 3
             * 3/3 * 100 = 100%
             * 2/3 * 100 = 66%
             * 1/3 * 100 = 33%
             * */

            val emoticonList = DataProvider.getEmoticonList()
            binding.emoticon.setImageResource(emoticonList[list.first].image)
            binding.progress.progress = ((list.second.toFloat() / max) * 100).toInt()
            binding.count.text = list.second.toString()
            if ((list.second / max) * 100 == 100) {
                binding.count.setTextColorRes(R.color.btn_color)
            }else{
                binding.count.setTextColorRes(R.color.gray_font)
            }
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
        hashmap = HashMap() /* = java.util.HashMap<kotlin.Int, kotlin.Int> */
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