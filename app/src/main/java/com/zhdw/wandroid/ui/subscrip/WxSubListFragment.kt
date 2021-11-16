package com.zhdw.wandroid.ui.subscrip

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adazhdw.adapter.list.decoration.LinearSpacingItemDecoration
import com.adazhdw.kthttp.coroutines.toClazz
import com.adazhdw.kthttp.getRequest
import com.adazhdw.ktlib.ext.dp2px
import com.adazhdw.ktlib.list.ListFragment
import com.zhdw.wandroid.constant.C
import com.zhdw.wandroid.http.BaseResponse
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.HomeArticleList
import com.zhdw.wandroid.ui.home.ArticleListAdapter


/**
 * Administrator
 * create at 2020/4/13 15:59
 * description:
 */
class WxSubListFragment : ListFragment<ArticleData, ArticleListAdapter>() {

    companion object {
        fun instance(id: Long): WxSubListFragment {
            return WxSubListFragment().apply {
                arguments = bundleOf("id" to id)
            }
        }
    }

    private val id by lazy { arguments?.getLong("id", 0) ?: 0 }

    override fun startAtPage(): Int = 0

    override fun onLoad(page: Int, callback: LoadDataCallback<ArticleData>) {
        launchOnUI(error = {
            callback.onFail(0,"")
        }) {
            val data = getRequest { url(C.BASE_URL + "/wxarticle/list/$id/$page/json") }
                .toClazz<BaseResponse<HomeArticleList>>().await().data
            val list = data?.datas ?: listOf()
            val currSize = mData.size + list.size
            callback.onSuccess(list, data?.total ?: 0 > currSize)
        }
    }

    override fun itemDecoration(): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(dp2px(15f), LinearLayoutManager.VERTICAL, true)
    }

    override fun getDataAdapter(): ArticleListAdapter = ArticleListAdapter()

}
