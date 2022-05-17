package com.mongsil.mongsildiary

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.mongsil.mongsildiary.base.BaseActivity
import com.mongsil.mongsildiary.databinding.ActivityMainBinding
import com.mongsil.mongsildiary.model.FabViewModel
import com.mongsil.mongsildiary.ui.calendar.CalendarFragment
import com.mongsil.mongsildiary.ui.setting.SettingFragment

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    private var backKeyPressedTime = 0L
    private var isOpen: Boolean = false
    private lateinit var fabOpen: Animation //TODO lateinit 사용 자제하기. 해당 변수가 초기화되지 않은 상태에서, 해당 변수를 참조하면, 에러가 발생함. lateinit으로 선언하면 이 변수가 언제 초기화되는지 시점을 체크해야 하는 불편함이 있음.
    private lateinit var fabClose: Animation
    private lateinit var fabRClockwise: Animation
    private lateinit var fabRAntiClockwise: Animation
    private val fabViewModel: FabViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setFloatingButtonAnimation()

//        fabViewModel.state.observe(this, Observer {
//            if (it == true) {
//                binding.fab.visibility = View.VISIBLE
//            } else {
//                binding.fab.visibility = View.GONE
//            }
//        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
//            "현재 위치 id값 :${destination.id}".log()
//            "timeSlotFragment id값 : ${R.id.timeSlotFragment}".log()
            if (destination.id == R.id.settingFragment ||
                destination.id == R.id.calendarFragment ||
                destination.id == R.id.homeFragment
            ) {
                binding.fab.visibility = View.VISIBLE
            } else {
                binding.fab.clearAnimation()
                binding.fab.visibility = View.GONE

            }
//            "$isOpen".log()
//            "visible 상태 : ${binding.fab.visibility}".log()
        }

        binding.fab.setOnClickListener {
            setOnClickFloatingButton(isOpen)
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

//        "${(navController.currentDestination as? FragmentNavigator.Destination)?.className}".log()
    }

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

    //TODO 뷰의 상태를 처리하는 코드를 없애기 위해 databinding 고려해보는 것이 좋을 듯.
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

    override fun onBackPressed() {
        if (binding.shadowView.visibility == View.VISIBLE) {
            binding.shadowView.visibility = View.GONE
            closedFab()
        } else {
            super.onBackPressed()
        }

    }
}

