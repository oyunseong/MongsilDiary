package com.mongsil.mongsildiary.base

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
        makeStatusBarTransparent()
    }

    fun Activity.makeStatusBarTransparent() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
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