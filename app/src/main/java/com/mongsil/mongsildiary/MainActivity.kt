package com.mongsil.mongsildiary

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.ActivityMainBinding
import com.mongsil.mongsildiary.domain.Saying
import com.mongsil.mongsildiary.server.GithubAPI
import com.mongsil.mongsildiary.server.RetrofitClient
import com.mongsil.mongsildiary.ui.calendar.CalendarFragment
import com.mongsil.mongsildiary.ui.home.HomeFragment
import com.mongsil.mongsildiary.ui.home.record.RecordFragment
import com.mongsil.mongsildiary.ui.setting.SettingFragment
import com.mongsil.mongsildiary.utils.printLog
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isOpen: Boolean = false
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var fabRClockwise: Animation
    private lateinit var fabRAntiClockwise: Animation
    private lateinit var recordBundle: Bundle

    private val mainViewModel by viewModels<MainViewModel>(factoryProducer = {
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 광고를 로드하기 전에 SDK를 초기화하고 초기화가 완료된 후(또는 30초의 제한 시간이 경과한 후에) 리스너를 다시 호출
        MobileAds.initialize(this) {}

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        mainViewModel.date.observe(this) {
            mainViewModel.getRecordData()
        }
        mainViewModel.recordData.observe(this) {
            recordBundle = bundleOf("record" to it)
        }

        setFloatingButtonAnimation()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.settingFragment ||
                destination.id == R.id.calendarFragment ||
                destination.id == R.id.homeFragment
            ) {
                binding.fab.visibility = View.VISIBLE
            } else {
                binding.fab.clearAnimation()
                binding.fab.visibility = View.GONE
            }
        }

        binding.fab.setOnClickListener {
            setOnClickFloatingButton(isOpen)
        }

        binding.recordFab.setOnClickListener {
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className == RecordFragment::class.java.name)
                return@setOnClickListener
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className != HomeFragment::class.java.name) {
                navController.popBackStack()
            }
            navController.navigate(R.id.action_homeFragment_to_recordFragment, recordBundle)
            closedFab()
            binding.fab.clearAnimation()
            binding.fab.visibility = View.GONE
        }

        binding.settingFab.setOnClickListener {
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className == SettingFragment::class.java.name)
                return@setOnClickListener
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className != HomeFragment::class.java.name) {
                navController.popBackStack()
            }
            navController.navigate(R.id.action_homeFragment_to_settingFragment)
            closedFab()
        }

        binding.calendarFab.setOnClickListener {
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className == CalendarFragment::class.java.name)
                return@setOnClickListener
            if ((navController.currentDestination as? FragmentNavigator.Destination)?.className != HomeFragment::class.java.name) {
                navController.popBackStack()
            }
            navController.navigate(R.id.action_homeFragment_to_calendarFragment)
            closedFab()
        }

        val adRequest = AdRequest.Builder().build()
        binding.bottomBanner.adListener
        binding.bottomBanner.loadAd(adRequest)
    }


    // TODO 애니메이션 개선 작업
    /***
     * @param fabOpen : 플로팅버튼이 켜질 때 애니메이션
     * @param fabClose : 플로팅버튼이 닫힐 때 애니메이션
     * @param fabRClockwise : 플로팅버튼이 켜질 때 회전하는 애니메이션
     * @param fabRAntiClockwise : 플로팅버튼이 켜질 때 회전하는 애니메이션
     */
    private fun setFloatingButtonAnimation() {
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
//        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)
        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
    }

    private val fabArray by lazy {
        arrayOf(
            binding.settingFab,
            binding.recordFab,
            binding.calendarFab,
            binding.settingTv,
            binding.recordTv,
            binding.calendarTv,
        )
    }

    private fun setOnClickFloatingButton(boolean: Boolean) {
        if (!boolean) {
            openFab()
        } else {
            closedFab()
        }
    }

    private fun openFab() {
        binding.fab.setImageResource(R.drawable.ic_x_32_close)
        fabArray.forEachIndexed { _, fab ->
            fab.startAnimation(fabOpen)
        }
        binding.fab.startAnimation(fabRAntiClockwise)

        binding.shadowView.visibility = View.VISIBLE
        binding.calendarFab.visibility = View.VISIBLE
        binding.recordFab.visibility = View.VISIBLE
        binding.settingFab.visibility = View.VISIBLE
        binding.calendarTv.visibility = View.VISIBLE
        binding.recordTv.visibility = View.VISIBLE
        binding.settingTv.visibility = View.VISIBLE

        binding.shadowView.bringToFront()
        binding.settingTv.bringToFront()
        binding.recordTv.bringToFront()
        binding.calendarTv.bringToFront()

        binding.calendarFab.isClickable = true
        binding.settingFab.isClickable = true
        binding.recordFab.isClickable = true

        isOpen = true
    }

    private fun closedFab() {
        binding.fab.setImageResource(R.drawable.ic_x_32_menu)
        fabArray.forEachIndexed { _, fab ->
            fab.startAnimation(fabClose)
        }
        binding.fab.startAnimation(fabRClockwise)
        binding.shadowView.visibility = View.GONE
        binding.calendarFab.visibility = View.GONE
        binding.recordFab.visibility = View.GONE
        binding.settingFab.visibility = View.GONE
        binding.calendarTv.visibility = View.GONE
        binding.recordTv.visibility = View.GONE
        binding.settingTv.visibility = View.GONE

        binding.calendarFab.isClickable = false
        binding.settingFab.isClickable = false
        binding.recordFab.isClickable = false

        isOpen = false
    }

    fun getCurrentFragment(): BaseFragment? {
        return supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.primaryNavigationFragment as? BaseFragment
    }

    override fun onBackPressed() {
        if (binding.shadowView.visibility == View.VISIBLE) {
            binding.shadowView.visibility = View.GONE
            closedFab()
        } else {
            super.onBackPressed()
        }
    }
}





