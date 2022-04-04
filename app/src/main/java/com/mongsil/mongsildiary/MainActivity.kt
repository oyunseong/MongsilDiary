package com.mongsil.mongsildiary

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityMainBinding
import com.mongsil.mongsildiary.ui.calendar.CalendarFragment
import com.mongsil.mongsildiary.ui.cheering.CheeringFragment
import com.mongsil.mongsildiary.ui.home.HomeFragment
import com.mongsil.mongsildiary.ui.setting.SettingFragment
import com.mongsil.mongsildiary.utils.log

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    //    private var navHostFragment: NavHostFragment =
//        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//    private var navController: NavController = navHostFragment.navController
    private var backKeyPressedTime = 0L
    private var isOpen: Boolean = false
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var fabRClockwise: Animation
    private lateinit var fabRAntiClockwise: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        settingFab()

        binding.fab.setOnClickListener {
            setFab(isOpen)
        }

        binding.settingFab.setOnClickListener {
            Toast.makeText(this, "setting 버튼을 클릭하셨습니다", Toast.LENGTH_SHORT).show()

            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className == SettingFragment::class.java.name)
                return@setOnClickListener
            navController.navigate(R.id.settingFragment)
            closedFab()
        }

        binding.calendarFab.setOnClickListener {
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className == CalendarFragment::class.java.name)
                return@setOnClickListener
            navController.navigate(R.id.calendarFragment)
            closedFab()
        }

        "${(navController.currentDestination as? FragmentNavigator.Destination)?.className}".log()

        if ((navController.currentDestination as? FragmentNavigator.Destination)?.className == HomeFragment::class.java.name
            || (navController.currentDestination as? FragmentNavigator.Destination)?.className == CalendarFragment::class.java.name
            || (navController.currentDestination as? FragmentNavigator.Destination)?.className == SettingFragment::class.java.name
            || (navController.currentDestination as? FragmentNavigator.Destination)?.className == CheeringFragment::class.java.name
        ) {
            binding.fab.visibility = View.VISIBLE
        } else {
            binding.fab.visibility = View.GONE
        }
    }

    private fun settingFab() {
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)
    }

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

    private fun setFab(boolean: Boolean) {
        if (!boolean) {
            fabArray.forEachIndexed { _, fab ->
                fab.startAnimation(fabOpen)
            }
            binding.fab.startAnimation(fabRAntiClockwise)
            binding.shadowView.visibility = View.VISIBLE
            binding.calendarFab.visibility = View.VISIBLE
            binding.cheeringFab.visibility = View.VISIBLE
            binding.settingFab.visibility = View.VISIBLE
            binding.shadowView.bringToFront()
            binding.settingTv.bringToFront()
            binding.cheeringTv.bringToFront()
            binding.calendarTv.bringToFront()

            binding.calendarFab.isClickable = true
            binding.settingFab.isClickable = true
            binding.cheeringFab.isClickable = true

            isOpen = true
        } else {
            closedFab()
        }
    }

    private fun closedFab() {
        fabArray.forEachIndexed { _, fab ->
            fab.startAnimation(fabClose)
        }
        binding.fab.startAnimation(fabRClockwise)
        binding.shadowView.visibility = View.GONE
        binding.calendarFab.visibility = View.GONE
        binding.cheeringFab.visibility = View.GONE
        binding.settingFab.visibility = View.GONE

        binding.calendarFab.isClickable = false
        binding.settingFab.isClickable = false
        binding.cheeringFab.isClickable = false

        isOpen = false
    }

    override fun onBackPressed() {
        if (binding.shadowView.visibility == View.VISIBLE) {
            binding.shadowView.visibility = View.GONE
            closedFab()
        } else {
            super.onBackPressed()
        }

//        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
//            backKeyPressedTime = System.currentTimeMillis()
//            return;
//        }
//        if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
//            finish()
//        }

    }

}

