package com.mongsil.mongsildiary.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater,container,false)
    }
}