package com.mongsil.mongsildiary.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private val _tag: String = this::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(_tag, "++onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(_tag, "++onViewCreated")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(_tag, "++onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(_tag, "++onDestroyView")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(_tag, "++onAttach")
    }

    override fun onStart() {
        super.onStart()
        Log.d(_tag, "++onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(_tag, "++onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(_tag, "++onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(_tag, "++onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(_tag, "++onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(_tag, "++onDetach")
    }
}