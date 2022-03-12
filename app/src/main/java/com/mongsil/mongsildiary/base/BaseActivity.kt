package com.mongsil.mongsildiary.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.mongsil.mongsildiary.R

abstract class BaseActivity<B: ViewBinding>(val bindingFactory: (LayoutInflater) -> B): AppCompatActivity(){
    private var _binding: B? =null
    val binding get() = _binding!!

    private val tag: String = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "++onCreate")
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        // 상태바 배경 색상 변경
        val window : Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_background)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "++onDestroy")
        _binding = null
    }

    fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "++onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "++onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "++onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "++onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "++onRestart")
    }


}