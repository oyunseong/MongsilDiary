package com.mongsil.mongsildiary.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding

//TODO BaseFragment 사용 자제하기. ViewBinding을 안쓰는 프래그먼트라면?
//TODO 컴포즈로 구성된 프래그먼트 혹은 데이터바인딩을 사용하는 프래그먼트일 경우 문제 발생
abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    private val _tag: String = this::class.java.name

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