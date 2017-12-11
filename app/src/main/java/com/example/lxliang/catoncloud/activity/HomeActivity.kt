package com.example.lxliang.catoncloud.activity

import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

import com.example.lxliang.catoncloud.R
import com.example.lxliang.catoncloud.adapter.BannerAdapter
import com.example.lxliang.catoncloud.fragment.MyCatFragment
import com.example.lxliang.catoncloud.fragment.NearbyCatFragment
import com.example.lxliang.catoncloud.presenter.LoginContract
import com.example.lxliang.catoncloud.presenter.MainContract
import com.example.lxliang.catoncloud.presenter.MainPresenter
import com.example.lxliang.catoncloud.utils.ViewPagerScroller
import java.util.*

class HomeActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, MainContract.View {
    // Message id for banner flip action
    private val message: Int = 0
    // Banner flip action interval
    private val interval = 4000L
    // Banner ViewPager initial position
    private val viewPagerInitItem = 1000

    @BindView(R.id.ad_viewpager)
    lateinit var mViewPager: ViewPager

    @BindView(R.id.indicator)
    lateinit var mIndicatorView: ViewGroup

    @BindView(R.id.left_tab)
    lateinit var mLeftTab: TextView

    @BindView(R.id.right_tab)
    lateinit var mRightTab: TextView

    // Banner image reource IDs
    private val mImageResIds = arrayListOf<Int>(R.mipmap.banner_icon_1,
            R.mipmap.banner_icon_2,
            R.mipmap.banner_icon_3,
            R.mipmap.banner_icon_4)
    // Record banner previous position
    private var previousPosition = 0

    private lateinit var mNearbyCatFragment: NearbyCatFragment
    private lateinit var mMyCatFragment: MyCatFragment

    private val mPresenter: MainContract.Presenter = MainPresenter(this)

    private lateinit var scroller: ViewPagerScroller

    @OnClick(R.id.left_tab)
    fun onLeftTabClicked() = mPresenter.switchToNearbyCat()

    @OnClick(R.id.right_tab)
    fun onRightTabClicked() = mPresenter.switchToMyCat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        setupViewPager()
        setupFragment()
    }

    override fun onResume() {
        super.onResume()
        mHandler.sendEmptyMessageDelayed(message, interval)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeMessages(message)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val newPosition = position % mImageResIds.size
        updateIndicator(previousPosition, newPosition)
        previousPosition = newPosition
    }

    override fun switchToNearbyCat() {
        mLeftTab.isEnabled = false
        mRightTab.isEnabled = true

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, mNearbyCatFragment)
        transaction.commit()
    }

    override fun switchToMyCat() {
        mLeftTab.isEnabled = true
        mRightTab.isEnabled = false

        mMyCatFragment = mMyCatFragment ?: MyCatFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, mMyCatFragment)
        transaction.commit()
    }

    private fun updateIndicator(previousPosition: Int, newPosition: Int) {
        var dot = mIndicatorView.getChildAt(previousPosition) as ImageView
        dot.setImageResource(R.drawable.banner_indicator_unselected)
        dot = mIndicatorView.getChildAt(newPosition) as ImageView
        dot.setImageResource(R.drawable.banner_indicator_selected)
    }

    private fun setupViewPager() {
        var mViewContainer = ArrayList<ImageView>()

        // Create ImageView for each image resource
        mImageResIds.forEach { id ->
            // Compare to XML, we can also create view with code
            val view = ImageView(this)
            view.scaleType = ImageView.ScaleType.FIT_XY
            // id as a pamameter. Use 'it' by default
            view.setImageResource(id)
            mViewContainer.add(view)
        }

        // Set ViewPager fields
        mViewPager.adapter = BannerAdapter(mViewContainer)
        mViewPager.addOnPageChangeListener(this)
        mViewPager.currentItem = viewPagerInitItem

        // Use ViewPagerScroller to control animation speed
        ViewPagerScroller(this).initViewPagerScroll(mViewPager)
    }

    private fun setupFragment() {
        mMyCatFragment = MyCatFragment()
        mNearbyCatFragment = NearbyCatFragment()
        var transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, mNearbyCatFragment)
        transaction.commit()
    }

    // Inherit from Handler and create an anoymous class
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            mViewPager.currentItem++
            this.sendEmptyMessageDelayed(message, interval)
        }
    }

}
