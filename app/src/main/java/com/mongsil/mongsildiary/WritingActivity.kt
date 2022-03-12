package com.mongsil.mongsildiary

import android.os.Bundle
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityWritingBinding

class WritingActivity :BaseActivity<ActivityWritingBinding>({ ActivityWritingBinding.inflate(it)}){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}