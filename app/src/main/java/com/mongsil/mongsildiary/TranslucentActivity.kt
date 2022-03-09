package com.mongsil.mongsildiary

import android.os.Bundle
import android.widget.Toast
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityTranslucentBinding

class TranslucentActivity : BaseActivity<ActivityTranslucentBinding>({ ActivityTranslucentBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this, "호출", Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)
        binding.translucentActivity.setOnClickListener {
            Toast.makeText(this, "화면 클릭하셨습니다", Toast.LENGTH_SHORT).show()
            finish()
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//            WindowManager.LayoutParams.FLAG_BLUR_BEHIND
//        )
    }
}