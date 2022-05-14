package com.mongsil.mongsildiary.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.mongsil.mongsildiary.model.FabViewModel
import com.mongsil.mongsildiary.utils.log

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    private var _binding: B? = null
    val binding get() = _binding!!
    private val _tag: String = this::class.java.name
    val fabViewModel: FabViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(_tag, "++onCreateView")
        _binding = getFragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabViewModel.setFabState(true)
        Log.d(_tag, "++onViewCreated")
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(_tag, "++onCreate")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(_tag, "++onDestroyView")
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(_tag, "++onAttach")
    }


    fun showToast(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
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