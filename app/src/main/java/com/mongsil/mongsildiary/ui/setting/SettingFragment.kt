package com.mongsil.mongsildiary.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.base.ViewBindingFragment
import com.mongsil.mongsildiary.databinding.FragmentSettingBinding
import com.mongsil.mongsildiary.utils.showToast

class SettingFragment : ViewBindingFragment<FragmentSettingBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title.setText(R.string.setting)
        binding.toolbar.uploadBtn.visibility = View.GONE

        binding.pushBtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_pushAllowFragment)
        }
        binding.backupBtn.setOnClickListener {
            findNavController().navigate(R.id.action_to_settingFragment_to_backupFragment)
        }

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.reviewBtn.setOnClickListener {
//            val packageName =
//                "com.cashproject.mongsil"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=" + requireContext().packageName)
            try {
                requireContext().startActivity(intent)
            }catch (e:Exception){
                e.printStackTrace()
                requireContext().showToast("플레이스토어를 찾을 수 없습니다.")
            }

        }
    }

}