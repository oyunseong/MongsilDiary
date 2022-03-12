package com.mongsil.mongsildiary

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val mainAdapter = MainAdapter(dataSet, object : MainAdapter.OnItemClickListener{
        override fun onClick(v: View, position: Int) {
            showToast("${position}번째 item")
        }
    })

    var backKeyPressedTime = 0L
    private var isOpen: Boolean = false
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var fabRClockwise: Animation
    private lateinit var fabRAntiClockwise: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val host = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment,host)
            .setPrimaryNavigationFragment(host)
            .commit()

//        addData()
//        initRecycler()
        settingFab()
    }
//
//    private fun initRecycler() {
//        binding.recycler.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.recycler.adapter = mainAdapter
//        binding.recycler.addItemDecoration(VerticalItemDecorator(10))
//        binding.recycler.addItemDecoration(HorizontalItemDecorator(10))
//    }
//
    private fun settingFab() {
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)

        binding.fab.setOnClickListener {
            setFab(isOpen)
            binding.settingFab.setOnClickListener {
                Toast.makeText(this, "setting 버튼을 클릭하셨습니다", Toast.LENGTH_SHORT).show()
                Navigation.createNavigateOnClickListener(R.id.calendarFragment)
            }
        }
    }
//
    private val fabArray by lazy {
        arrayOf(
            binding.settingFab,
            binding.cheeringFab,
            binding.calendarFab,
            binding.settingTv,
            binding.cheeringTv,
            binding.calendarTv,
        )
    }
//
    private fun setFab(boolean: Boolean) {
        if (!boolean) {
            fabArray.forEachIndexed { _, fab ->
                fab.startAnimation(fabOpen)
            }
            binding.fab.startAnimation(fabRAntiClockwise)
            binding.shadowView.visibility = View.VISIBLE
            binding.shadowView.bringToFront()
            binding.settingTv.bringToFront()
            binding.cheeringTv.bringToFront()
            binding.calendarTv.bringToFront()

            binding.settingFab.isClickable
            binding.cheeringFab.isClickable
            binding.calendarFab.isClickable

            isOpen = true
        } else {
            fabArray.forEachIndexed { _, fab ->
                fab.startAnimation(fabClose)
            }
            binding.fab.startAnimation(fabRClockwise)
            binding.shadowView.visibility = View.GONE
            isOpen = false
        }
    }

//
//    private fun addData() {
//        for (i in 0..99) {
//            dataSet.add(listOf("$i th main", "$i th sub"))
//        }
//    }
//
//    override fun onBackPressed() {
////        super.onBackPressed()
//
//        if (binding.shadowView.visibility == View.VISIBLE) {
//            binding.shadowView.visibility = View.GONE
//            setFab(true)
//        }
//        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
//            backKeyPressedTime = System.currentTimeMillis()
//            return;
//        }
//        if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
//            finish()
//        }
//    }
}

