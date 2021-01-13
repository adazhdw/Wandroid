package com.zhdw.wandroid.ui.search

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adazhdw.adapter.list.decoration.LinearSpacingItemDecoration
import com.adazhdw.ktlib.base.mvvm.viewModel
import com.adazhdw.ktlib.ext.addFragment
import com.adazhdw.ktlib.ext.dp2px
import com.adazhdw.ktlib.ext.showFragment
import com.adazhdw.ktlib.ext.view.invisible
import com.adazhdw.ktlib.ext.view.visible
import com.adazhdw.ktlib.list.ListFragment
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseActivityBinding
import com.zhdw.wandroid.databinding.ActivitySearchBinding
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.hideFragment
import com.zhdw.wandroid.ui.home.ArticleListAdapter
import com.zhdw.wandroid.ui.vm.SearchRepository
import com.zhdw.wandroid.ui.vm.SearchViewModel
import com.zhdw.wandroid.widget.onTagClick

class SearchActivity : BaseActivityBinding() {

    private lateinit var viewBinding: ActivitySearchBinding
    private val viewModel by lazy { viewModel<SearchViewModel>() }
    private val articlesFragment: SearchArticlesFragment by lazy { SearchArticlesFragment() }

    override fun initViewBinding(): ViewBinding {
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView() {
        viewBinding.searchEt.doAfterTextChanged {
            it?.let {
                if (it.toString().isNotBlank()) {
                    showList()
                    onSearch(it.toString())
                } else hideList()
            }
        }
        viewBinding.toolbar.setNavigationOnClickListener { finish() }
        addFragment(articlesFragment, R.id.searchListLayout)
    }

    override fun initData() {
        viewModel.hotKeys.observe(this, Observer { keys ->
            viewBinding.searchTag.tags = keys.map { key -> key.name }
            viewBinding.searchTag.onTagClick {
                showList()
                onTagClick(keys[it])
            }
        })
    }

    private fun onTagClick(key: HotKey) {
        viewBinding.searchEt.setText(key.name)
        viewBinding.searchEt.setSelection(key.name.length)
    }

    private fun onSearch(key: String) {
        articlesFragment.setKey(key)
    }

    private fun showList() {
        viewBinding.searchListLayout.visible()
        viewBinding.searchTag.invisible()
        showFragment(articlesFragment)
    }

    private fun hideList() {
        viewBinding.searchListLayout.invisible()
        viewBinding.searchTag.visible()
        hideFragment(articlesFragment)
    }

    override fun requestStart() {
        viewModel.getHotKeys()
    }

}

class SearchArticlesFragment : ListFragment<ArticleData, ArticleListAdapter>() {

    override fun itemDecoration(): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(dp2px(15f), LinearLayoutManager.VERTICAL, true)
    }

    private val repository by lazy { SearchRepository() }
    private var key: String? = null
    override fun onLoad(page: Int, callback: LoadDataCallback<ArticleData>) {
        key?.let {
            launchOnUI {
                val data = repository.searchArticles(page, it).data
                val datas = data?.datas
                if (datas != null) {
                    callback.onSuccess(datas, mData.size < data.total)
                }
            }
        }
    }

    override fun getDataAdapter(): ArticleListAdapter = ArticleListAdapter()

    fun setKey(key: String) {
        this.key = key
        refresh()
    }

}
