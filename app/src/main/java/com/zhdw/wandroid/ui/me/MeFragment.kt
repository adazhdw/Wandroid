package com.zhdw.wandroid.ui.me

import android.view.View
import androidx.viewbinding.ViewBinding
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentMeBinding

/**
 * Created by daguozhu
 * at: 2020/3/10 14:21.
 * desc:
 */
class MeFragment :BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentMeBinding

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentMeBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initData() {

    }

    override fun initView(view: View) {
        viewBinding.meMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.msgCenter -> toMsgPage()
                R.id.articleCollect -> toMsgPage()
                R.id.articleShare -> toMsgPage()
                R.id.websiteCollect -> toMsgPage()
                R.id.projectShare -> toMsgPage()
            }
            true
        }
    }

    private fun toMsgPage() {

    }

    override fun requestStart() {

    }

}