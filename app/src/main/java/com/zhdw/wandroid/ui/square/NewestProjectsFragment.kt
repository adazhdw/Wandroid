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
import com.zhdw.wandroid.databinding.FragmentNewestProjectsBinding
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.project.ProjectListAdapter
import com.zhdw.wandroid.ui.vm.HomeRepository


/**
 * Administrator
 * create at 2020/4/22 14:18
 * description:
 */
class NewestProjectsFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentNewestProjectsBinding
    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentNewestProjectsBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView(view: View) {
        addFragment(NewestProjectsListFragment(), R.id.container)
    }

}
class NewestProjectsListFragment : ListFragment<ArticleData, ProjectListAdapter>() {

    override fun itemDecoration(): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(dp2px(15f), LinearLayoutManager.VERTICAL, true)
    }

    private val mRepository by lazy { HomeRepository() }

    override fun onLoad(page: Int, callback: LoadDataCallback<ArticleData>) {
        launchOnUI {
            val homeData = mRepository.getHomeProjectList(page).data
            val homeList = homeData?.datas ?: listOf()
            callback.onSuccess(homeList, mData.size < (homeData?.total ?: 0))
        }
    }

    override fun getDataAdapter(): ProjectListAdapter = ProjectListAdapter()

}