package com.mongsil.mongsildiary.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mongsil.mongsildiary.databinding.ThisMonthMongsilBinding
import com.mongsil.mongsildiary.ui.home.today.Emoticon

class ThisMonthMongsilAdapter() :
    RecyclerView.Adapter<ThisMonthMongsilAdapter.ThisMonthMongsilViewHolder>() {

    private val thisMonthMongsilList: List<Emoticon> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThisMonthMongsilViewHolder {
        val binding =
            ThisMonthMongsilBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ThisMonthMongsilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThisMonthMongsilViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return thisMonthMongsilList.size
    }

    class ThisMonthMongsilViewHolder(private val binding: ThisMonthMongsilBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }


    }
}