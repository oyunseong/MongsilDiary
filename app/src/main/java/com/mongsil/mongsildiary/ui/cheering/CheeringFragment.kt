package com.mongsil.mongsildiary.ui.cheering

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCheeringBinding

class CheeringFragment :BaseFragment<FragmentCheeringBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheeringBinding {
        return FragmentCheeringBinding.inflate(inflater,container,false)
    }
}