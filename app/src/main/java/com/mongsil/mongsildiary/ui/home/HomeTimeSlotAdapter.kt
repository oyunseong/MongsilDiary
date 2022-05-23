package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding

class HomeTimeSlotAdapter(
    private val homeTimeSlotList: ArrayList<List<String>>,
//    private val onItemClickListener: OnItemClickListener //TODO 람다 방식으로 아이템 클릭 리스너 처리해보기. 람다를 이용하면 인터페이스를 만들어야하는 번거러움을 줄일 수 있음.
) : RecyclerView.Adapter<HomeTimeSlotAdapter.HomeTimeSlotListViewHolder>() {

    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTimeSlotListViewHolder {
        val binding =
            ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val homeTimeSlotListViewHolder = HomeTimeSlotListViewHolder(binding)

        binding.constraint.setOnClickListener {

            val position =  homeTimeSlotListViewHolder.absoluteAdapterPosition

            onItemClickListener?.invoke(position)
        }

        return HomeTimeSlotListViewHolder(binding)
    }

    override fun getItemCount(): Int = homeTimeSlotList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: HomeTimeSlotListViewHolder, position: Int) {

//        holder.itemView.setOnClickListener {
//            onItemClickListener.onClick(it, position)
//        }

        holder.bind(homeTimeSlotList[position])
    }

    class HomeTimeSlotListViewHolder(private val binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<String>) { //TODO bind 함수의 파라미터가 리스트일 필요가 없음. 고치기
            binding.timeslot.text =
                data[0] //TODO data의 size가 0이면, 앱이 터질 것임. 애초에 여기에 리스트를 넘기면 안됨. 모든 아이템에 0번째 데이터만 표시될 것
            binding.contents.visibility = View.GONE
            binding.slotImg.visibility = View.GONE
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
}