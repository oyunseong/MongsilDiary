package com.mongsil.mongsildiary.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

//TODO BaseFragment처럼 viewBinding Activity, DataBindingActivity 만들기
abstract class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    private var _binding: B? = null
    val binding get() = _binding!!

    private val tag: String = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "++onCreate")
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "++onDestroy")
        _binding = null
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