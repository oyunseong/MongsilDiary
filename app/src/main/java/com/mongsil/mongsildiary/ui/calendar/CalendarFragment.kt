package com.mongsil.mongsildiary.ui.calendar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.mongsil.mongsildiary.MainViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.AdUnifiedBinding
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding
import com.mongsil.mongsildiary.dialog.DayPickerDialogX
import com.mongsil.mongsildiary.ui.home.today.DataProvider
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.printLog
import com.prolificinteractive.materialcalendarview.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate


class CalendarFragment : BaseFragment(), OnDateSelectedListener {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val calendarViewModel by viewModels<CalendarViewModel>()
    private val dayPickerDialog = DayPickerDialogX()
    private val thisMonthMongsilAdapter = ThisMonthMongsilAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544~3347511713"
    }

    private fun populateNativeAdView(nativeAd: NativeAd, unifiedAdBinding: AdUnifiedBinding) {
        val nativeAdView = unifiedAdBinding.root

        // Set the media view.
        nativeAdView.mediaView = unifiedAdBinding.adMedia

        // Set other ad assets.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline
        nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
        nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
        nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline
        nativeAd.mediaContent?.let { unifiedAdBinding.adMedia.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            unifiedAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.VISIBLE
            unifiedAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            unifiedAdBinding.adPrice.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adPrice.visibility = View.VISIBLE
            unifiedAdBinding.adPrice.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            unifiedAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStore.visibility = View.VISIBLE
            unifiedAdBinding.adStore.text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */
    var currentNativeAd: NativeAd? = null
    private fun refreshAd() {
        val builder = AdLoader.Builder(requireContext(), AD_UNIT_ID)

        builder.forNativeAd { nativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            // If this callback occurs after the activity is destroyed, you must call
            // destroy and return or you may get a memory leak.
            var activityDestroyed = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activityDestroyed = requireActivity().isDestroyed
            }
            if (activityDestroyed || requireActivity().isFinishing || requireActivity().isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }
            // You must call destroy on old ads when you are done with them,
            // otherwise you will have a memory leak.
            currentNativeAd?.destroy()
            currentNativeAd = nativeAd
            val unifiedAdBinding = AdUnifiedBinding.inflate(layoutInflater)
            populateNativeAdView(nativeAd, unifiedAdBinding)
            binding.calendarAdmob.removeAllViews()
            binding.calendarAdmob.addView(unifiedAdBinding.root)
        }

        val adLoader =
            builder
                .withAdListener(
                    object : AdListener() {
                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            loadAdError.toString().printLog("Ad 에러 ")
                        }
                    }
                )
                .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setThisMonthMongsilRecycler()
        MobileAds.initialize(requireContext()) {}
        refreshAd()

//        MobileAds.initialize(requireContext())
//        val adLoader = AdLoader.Builder(requireContext(), AD_UNIT_ID)
//            .forNativeAd { nativeAd ->
//                val styles =
//                    NativeTemplateStyle.Builder().withMainBackgroundColor(ColorDrawable(33333))
//                        .build()
//                val template: TemplateView = binding.calendarAdmob
//                template.setStyles(styles)
//                template.setNativeAd(nativeAd)
//            }
//            .build()
//
//        adLoader.loadAd(AdRequest.Builder().build())

        binding.toolbar.apply {
            title.setText(R.string.calendar)
            uploadBtn.visibility = View.GONE
            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        mainViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = Date().plusDotCalendarDay(it)
        }

        binding.calendar.apply {
            setOnDateChangedListener(this@CalendarFragment)
            addDecorators(
                SaturdayDecorator(),
                SundayDecorator(),
                TodayDecorator(),
            )

            /**
             * Calendar 스크롤 시 해당 달 DB date 조회
             * Ex) SELECT * From SlotEntity date LIKE "202208__"
             * */
            setOnMonthChangedListener { widget, date ->
                calendarViewModel.apply {
                    setSlotData(date)
                    setRecordData(date)
                }
            }
        }

        calendarViewModel.apply {
            slotData.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    binding.thisMonthMongsilEmptyText.visibility = View.VISIBLE
                } else {
                    binding.thisMonthMongsilEmptyText.visibility = View.GONE
                }
                calendarViewModel.getSlotData()
                thisMonthMongsilAdapter.setMongsilList(it)
            }


            eventList.observe(viewLifecycleOwner) {
                it.forEach { (k, v) ->
                    binding.calendar.addDecorator(EventDecorator(k, v))
                }
            }
            recordData.observe(viewLifecycleOwner) {
                calendarViewModel.getRecordData()
            }
        }
    }

    inner class TodayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == CalendarDay.today()
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(StyleSpan(Typeface.BOLD))
        }
    }

    inner class SaturdayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val date: LocalDate = day.date
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            return dayOfWeek.value == 6
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.calendar_weekend
                    )
                )
            )
        }
    }

    inner class SundayDecorator : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            val date = day.date
            val dayOfWeek = date.dayOfWeek
            return dayOfWeek.value == 7
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.calendar_weekend
                    )
                )
            )
            view.setDaysDisabled(true)
        }
    }

    inner class EventDecorator(val dates: CalendarDay, val emoticonId: Int) :
        DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return dates == day
        }

        override fun decorate(view: DayViewFacade) {
            val emoticonList = DataProvider.getEmoticonList()
            val bitmap: Bitmap = BitmapFactory.decodeResource(
                requireContext().resources,
                emoticonList[emoticonId].image
            )
            view.setSelectionDrawable(BitmapDrawable(resources, bitmap))

            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparency
                    )
                )
            )

        }
    }

    /**
     * Calender Day Click event
     *
     * */
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        mainViewModel.setDate(date = date)
        findNavController().popBackStack()
    }

    private fun setThisMonthMongsilRecycler() {
        binding.mongsilRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mongsilRecycler.adapter = thisMonthMongsilAdapter
        binding.mongsilRecycler.addItemDecoration(HorizontalItemDecorator(20))
    }
}
