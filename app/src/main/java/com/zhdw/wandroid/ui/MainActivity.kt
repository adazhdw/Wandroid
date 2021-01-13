package com.zhdw.wandroid.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.ext.addFragment
import com.adazhdw.ktlib.ext.fullscreen
import com.adazhdw.ktlib.ext.showFragment
import com.adazhdw.ktlib.ext.transparent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseActivityBinding
import com.zhdw.wandroid.databinding.ActivityMainBinding
import com.zhdw.wandroid.ui.hideFragment
import com.zhdw.wandroid.ui.home.HomeFragment
import com.zhdw.wandroid.ui.me.MeFragment
import com.zhdw.wandroid.ui.project.ProjectsFragment
import com.zhdw.wandroid.ui.square.SquareFragment
import com.zhdw.wandroid.ui.subscrip.SubscriptionFragment

class MainActivity : BaseActivityBinding() {

    private val mHomeFragment by lazy { HomeFragment() }
    private val mMeFragment by lazy { MeFragment() }
    private val mProjectsFragment by lazy { ProjectsFragment() }
    private val mSquareFragment by lazy { SquareFragment() }
    private val mSubFragment by lazy { SubscriptionFragment() }

    private var mCurrentFragment: Fragment? = null
    private lateinit var viewBinding: ActivityMainBinding

    override fun initViewBinding(): ViewBinding {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView() {
        viewBinding.bottomNav.setTextVisibility(true)
        viewBinding.bottomNav.onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeItem -> showFragmentMain(mHomeFragment)
                    R.id.systemItem -> showFragmentMain(mSquareFragment)
                    R.id.subscribeItem -> showFragmentMain(mSubFragment)
                    R.id.projectsItem -> showFragmentMain(mProjectsFragment)
                    R.id.meItem -> showFragmentMain(mMeFragment)
                }
                true
            }
        viewBinding.bottomNav.currentItem = 0
    }

    override fun initData() {

    }

    private fun showFragmentMain(fragment: Fragment) {
        if (mCurrentFragment == fragment) {
            if (fragment is HomeFragment) {
                fragment.scrollToTop()
            }
            return
        }
        if (mCurrentFragment != null) {
            hideFragment(mCurrentFragment!!)
        }
        if (!fragment.isAdded) {
            addFragment(fragment, R.id.container)
            showFragment(fragment)
            mCurrentFragment = fragment
        } else if (fragment.isHidden) {
            showFragment(fragment)
            mCurrentFragment = fragment
        }
    }

    override fun onBackPressed() {
        /*if (viewBinding.bottomNav.currentItem != 0) {
            viewBinding.bottomNav.currentItem = 0
            return
        }*/
        super.onBackPressed()
    }
}
