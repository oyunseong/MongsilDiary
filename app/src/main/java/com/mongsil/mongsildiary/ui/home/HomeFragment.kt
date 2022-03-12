package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }
}