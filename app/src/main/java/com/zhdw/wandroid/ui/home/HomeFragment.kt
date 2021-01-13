package com.zhdw.wandroid.ui.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.base.mvvm.viewModel
import com.adazhdw.ktlib.ext.startActivity
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentHomeBinding
import com.zhdw.wandroid.ui.FriendWeb
import com.zhdw.wandroid.ui.glide
import com.zhdw.wandroid.ui.search.SearchActivity
import com.zhdw.wandroid.ui.vm.HomeViewModel
import com.zhdw.wandroid.ui.web.startWeb
import com.zhdw.wandroid.widget.onTagClick


/**
 * Created by daguozhu
 * at: 2020/3/10 14:21.
 * desc:
 */

class HomeFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val bannerAdapter by lazy { HomeBannerAdapter(context) }
    private val viewModel by lazy { viewModel<HomeViewModel>() }
    private val friendWebs = mutableListOf<FriendWeb>()

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initData() {
        viewModel.bannerData.observe(this, Observer {
            viewBinding.swipe.isRefreshing = false
            if (it.isNotEmpty()) {
                bannerAdapter.setDatas(it)
                bannerAdapter.notifyDataSetChanged()
            }
        })
        viewModel.friendWeb.observe(this, Observer {
            friendWebs.clear()
            friendWebs.addAll(it)
            viewBinding.tagContainer.tags = it.map { tag -> tag.name }
        })

        viewBinding.interviewIv.glide("https://www.wanandroid.com/blogimgs/b1bd944a-4a9e-4722-81c5-079676422c5e.jpg")
        viewBinding.interviewIv.setOnClickListener { }
        viewBinding.qaIv.glide("https://www.wanandroid.com/blogimgs/9d04f303-fc08-4582-b50d-4dedb1f566c9.jpg")
        viewBinding.qaIv.setOnClickListener { }
    }

    override fun initView(view: View) {
        viewBinding.swipe.setOnRefreshListener {
            requestStart()
        }
        viewBinding.banner.adapter = bannerAdapter
        viewBinding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> onSearchClick()
            }
            true
        }

        viewBinding.tagContainer.onTagClick{
            context?.startWeb(friendWebs[it].link)
        }
    }

    private fun onSearchClick() {
        startActivity<SearchActivity>()
    }

    override fun requestStart() {
        if (!viewBinding.swipe.isRefreshing) viewBinding.swipe.isRefreshing = true
        viewModel.getBanner()
        viewModel.getFriendWeb()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            viewBinding.banner.stop()
        } else {
            viewBinding.banner.start()
        }
    }

    override fun onPause() {
        super.onPause()
        viewBinding.banner.stop()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.banner.start()
    }

    fun scrollToTop() {
        viewBinding.nestedScrollView.scrollTo(0, 0)
    }

}
