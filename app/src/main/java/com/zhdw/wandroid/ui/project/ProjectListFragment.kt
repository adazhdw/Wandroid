package com.zhdw.wandroid.ui.project

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


/**
 * dgz
 * create at 2020/4/13 15:59
 * description:
 */
class ProjectListFragment : ListFragment<ArticleData, ProjectListAdapter>() {

    companion object {
        fun instance(id: Long): ProjectListFragment {
            return ProjectListFragment().apply {
                arguments = bundleOf("id" to id)
            }
        }
    }

    override fun itemDecoration(): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(dp2px(15f), LinearLayoutManager.VERTICAL, true)
    }

    private val id by lazy { arguments?.getLong("id", 0) ?: 0 }

    override fun startAtPage(): Int = 0

    override fun onLoad(page: Int, callback: LoadDataCallback<ArticleData>) {
        launchOnUI {
            val data = getRequest {
                url(C.BASE_URL + "/project/list/$page/json")
                queryParams("cid", "$id")
            }.toClazz<BaseResponse<HomeArticleList>>().await().data
            val list = data?.datas ?: listOf()
            val currSize = mData.size + list.size
            callback.onSuccess(list, currSize < (data?.total ?: 0))
        }
    }

    override fun getDataAdapter(): ProjectListAdapter = ProjectListAdapter()

}
