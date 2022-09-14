package com.mongsil.mongsildiary.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.ViewBindingFragment
import com.mongsil.mongsildiary.databinding.FragmentBackupBinding
import com.mongsil.mongsildiary.utils.showToast

class BackupFragment : ViewBindingFragment<FragmentBackupBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBackupBinding {
        return FragmentBackupBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showToast("준비중입니다.")

        binding.toolbar.title.text = resources.getString(R.string.diary_backup)
        binding.toolbar.uploadBtn.visibility = View.GONE

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}