package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ItemDiaryListBinding

//TODO 클래스 네이밍 신경 쓰기. 홈 화면의 어떤 목록의 어댑터인지 쓰는 게 좋을 듯. 어떤 리스트를 구성하는 어댑터인지 이름만 봐서는 알 수 없음
class HomeAdapter(
    private val dataSet: ArrayList<List<String>>, //TODO 데이터셋도 어떤 데이터셋인지 네이밍
    private val onItemClickListener: OnItemClickListener //TODO 람다 방식으로 아이템 클릭 리스너 처리해보기. 람다를 이용하면 인터페이스를 만들어야하는 번거러움을 줄일 수 있음.
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(it, position)
        }

        holder.bind(dataSet[position])
    }

    //TODO 뷰홀더 이름 정하기. 어떤 아이템의 뷰홀더인지.
    class ViewHolder(private val binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<String>) { //TODO bind 함수의 파라미터가 리스트일 필요가 없음. 고치기
            binding.timeslot.text = data[0] //TODO data의 size가 0이면, 앱이 터질 것임. 애초에 여기에 리스트를 넘기면 안됨. 모든 아이템에 0번째 데이터만 표시될 것
            binding.contents.visibility = View.GONE
            binding.slotImg.visibility = View.GONE
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
}