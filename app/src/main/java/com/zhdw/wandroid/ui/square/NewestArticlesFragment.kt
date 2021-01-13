package com.zhdw.wandroid.ui.square

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adazhdw.adapter.list.decoration.LinearSpacingItemDecoration
import com.adazhdw.ktlib.ext.addFragment
import com.adazhdw.ktlib.ext.dp2px
import com.adazhdw.ktlib.list.ListFragment
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentNewestArticlesBinding
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.home.ArticleListAdapter
import com.zhdw.wandroid.ui.vm.HomeRepository


/**
 * Administrator
 * create at 2020/4/22 14:09
 * description:
 */
class NewestArticlesFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentNewestArticlesBinding
    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentNewestArticlesBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView(view: View) {
        addFragment(NewestArticlesListFragment(), R.id.container)
    }

}
class NewestArticlesListFragment() : ListFragment<ArticleData, ArticleListAdapter>() {

    override fun itemDecoration(): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(dp2px(15f), LinearLayoutManager.VERTICAL, true)
    }

    private val mRepository by lazy { HomeRepository() }

    override fun onLoad(page: Int, callback: LoadDataCallback<ArticleData>) {
        launchOnUI {
            val list = if (page == 0) {
                mRepository.getPinArticle().data ?: listOf()
            } else listOf()
            list.forEach { it.isPinTop = true }
            val homeData = mRepository.getHomeArticleList(page).data
            val homeList = homeData?.datas ?: listOf()
            val data = list.toMutableList().apply { addAll(homeList) }
            callback.onSuccess(data, mData.size < (homeData?.total ?: 0))
        }
    }

    override fun getDataAdapter(): ArticleListAdapter = ArticleListAdapter()

}