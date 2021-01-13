package com.zhdw.wandroid.ui.vm

import androidx.lifecycle.MutableLiveData
import com.adazhdw.kthttp.coroutines.toClazz
import com.adazhdw.kthttp.entity.Param
import com.adazhdw.kthttp.ext.httpRequest
import com.adazhdw.ktlib.base.mvvm.BaseRepository
import com.adazhdw.ktlib.base.mvvm.BaseViewModel
import com.zhdw.wandroid.constant.C
import com.zhdw.wandroid.http.BaseResponse
import com.zhdw.wandroid.http.ListResponse
import com.zhdw.wandroid.ui.HomeArticleList
import com.zhdw.wandroid.ui.search.HotKey


/**
 * Administrator
 * create at 2020/4/23 19:46
 * description:
 */
class SearchViewModel : BaseViewModel<SearchRepository>() {
    override fun obtainRepository(): SearchRepository = SearchRepository()

    val hotKeys: MutableLiveData<List<HotKey>> = MutableLiveData()

    fun getHotKeys() {
        launch {
            val data = mRepository.getHotKeys()
            val list = data.data
            hotKeys.postValue(list)
        }
    }
}

class SearchRepository : BaseRepository() {
    suspend fun getHotKeys(): ListResponse<HotKey> {
        return httpRequest { url(C.BASE_URL + "/hotkey/json") }.toClazz<ListResponse<HotKey>>().await()
    }

    suspend fun searchArticles(page: Int, k: String): BaseResponse<HomeArticleList> {
        return httpRequest {
            url(C.BASE_URL + "/article/query/$page/json")
            addParam("k", k)
        }.toClazz<BaseResponse<HomeArticleList>>().await()
    }
}