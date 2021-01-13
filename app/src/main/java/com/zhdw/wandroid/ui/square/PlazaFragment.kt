package com.zhdw.wandroid.ui.square

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adazhdw.adapter.list.decoration.LinearSpacingItemDecoration
import com.adazhdw.ktlib.base.mvvm.viewModel
import com.adazhdw.ktlib.ext.dp2px
import com.adazhdw.ktlib.list.ListFragment
import com.adazhdw.ktlib.list.view.LoadMoreRecyclerView
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentPlazaBinding
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.home.ArticleListAdapter
import com.zhdw.wandroid.ui.vm.SquareRepository
import com.zhdw.wandroid.ui.vm.SquareViewModel
import com.zhdw.wandroid.widget.LinearLayoutManagerWrapper


/**
 * Administrator
 * create at 2020/4/9 16:09
 * description:
 */
class PlazaFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentPlazaBinding
    private val listAdapter by lazy { ArticleListAdapter() }
    private val viewModel by lazy { viewModel<SquareViewModel>() }
    private var currPage = 0

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentPlazaBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initData() {
        viewModel.plazaList.observe(this, Observer {
            if (it.isNotEmpty()) {
                currPage += 1
            }
            if (viewBinding.swipe.isRefreshing) {
                viewBinding.swipe.isRefreshing = false
                listAdapter.setData(it)
                if (it.isNotEmpty()) {
                    viewBinding.plazaRV.scrollToPosition(0)
                }
            } else {
                listAdapter.addData(it)
            }
            viewBinding.plazaRV.loadComplete(false)
        })
    }

    override fun initView(view: View) {
        viewBinding.swipe.setOnRefreshListener {
            currPage = 0
            requestStart()
        }
        viewBinding.plazaRV.layoutManager =
            LinearLayoutManagerWrapper(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.plazaRV.adapter = listAdapter
        viewBinding.plazaRV.setLoadMoreListener(object : LoadMoreRecyclerView.LoadMoreListener {
            override fun onLoadMore() {
                viewModel.getPlazaList(currPage)
            }
        })
    }

    override fun requestStart() {
        viewModel.getPlazaList(currPage)
    }
}

class PlazaFragment2 : ListFragment<ArticleData, ArticleListAdapter>() {

    override fun itemDecoration(): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(dp2px(15f), LinearLayoutManager.VERTICAL, true)
    }

    private val repository by lazy { SquareRepository() }

    override fun onLoad(page: Int, callback: LoadDataCallback<ArticleData>) {
        launchOnUI(error = {
            callback.onFail(0, "")
        }) {
            val data = repository.getPlazaList(page).data
            val list = data?.datas ?: listOf()
            val currSize = mData.size + list.size
            callback.onSuccess(list, data?.total ?: 0 > currSize)
        }
    }

    override fun getDataAdapter(): ArticleListAdapter = ArticleListAdapter()

}
