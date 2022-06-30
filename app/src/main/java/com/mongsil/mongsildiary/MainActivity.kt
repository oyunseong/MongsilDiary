package com.mongsil.mongsildiary

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.databinding.ActivityMainBinding
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.ui.calendar.CalendarFragment
import com.mongsil.mongsildiary.ui.setting.SettingFragment
import com.mongsil.mongsildiary.utils.printLog
import kotlinx.coroutines.launch
import java.util.*


// TODO 뷰모델 하나 만들고 뷰모델에 UI 상태를 저장하는 라이브 데이터 선언하고 라이브데이터를 XML에서 바인드 시켜주면됨
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isOpen: Boolean = false
    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private lateinit var fabRClockwise: Animation
    private lateinit var fabRAntiClockwise: Animation
//    lateinit var repo: DiaryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity
        // dbTestButton()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

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
    }

    // TODO 애니메이션 개선 작업
    /***
     * @param fabOpen : 플로팅버튼이 켜질 때 애니메이션
     * @param fabCLose : 플로팅버튼이 닫힐 때 애니메이션
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

    fun dbTestButton() {
        val repo = DiaryRepository(AppDatabase.getInstance(applicationContext).diaryDao())
        binding.testRecord.setOnClickListener {
            lifecycleScope.launch {
                repo.insertRecord(Record.mockRecord)
                repo.getRecordByDate(100).toString().printLog("record")

                repo.insertSlot(Slot.mockSlot)
                repo.getSlotsByDate(100).toString().printLog("slot")
            }
        }
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





