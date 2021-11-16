package com.zhdw.wandroid.ui.vm

import androidx.lifecycle.MutableLiveData
import com.adazhdw.kthttp.coroutines.toClazz
import com.adazhdw.kthttp.getRequest
import com.adazhdw.ktlib.base.mvvm.BaseRepository
import com.adazhdw.ktlib.base.mvvm.BaseViewModel
import com.zhdw.wandroid.constant.C
import com.zhdw.wandroid.http.BaseResponse
import com.zhdw.wandroid.http.ListResponse
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.BannerData
import com.zhdw.wandroid.ui.FriendWeb
import com.zhdw.wandroid.ui.HomeArticleList


/**
 * Administrator
 * create at 2020/4/2 17:25
 * description:
 */
class HomeViewModel : BaseViewModel<HomeRepository>() {
    override fun obtainRepository(): HomeRepository {
        return HomeRepository()
    }

    val bannerData: MutableLiveData<List<BannerData>> = MutableLiveData()
    val friendWeb: MutableLiveData<List<FriendWeb>> = MutableLiveData()

    fun getBanner() {
        launch {
            val list = mRepository.getBanner().data
            bannerData.postValue(list)
        }
    }

    fun getFriendWeb() {
        launch {
            val list = mRepository.getFriendWeb().data
            friendWeb.postValue(list)
        }
    }
}

class HomeRepository : BaseRepository() {
    suspend fun getBanner(): ListResponse<BannerData> {
        return getRequest { url(C.BASE_URL + "/banner/json") }.toClazz<ListResponse<BannerData>>().await()
    }

    suspend fun getFriendWeb(): ListResponse<FriendWeb> {
        return getRequest { url(C.BASE_URL + "/friend/json") }.toClazz<ListResponse<FriendWeb>>().await()
    }

    suspend fun getPinArticle(): ListResponse<ArticleData> {
        return getRequest { url(C.BASE_URL + "/article/top/json") }.toClazz<ListResponse<ArticleData>>()
                .await()
    }

    suspend fun getHomeArticleList(page: Int): BaseResponse<HomeArticleList> {
        return getRequest { url(C.BASE_URL + "/article/list/$page/json") }.toClazz<BaseResponse<HomeArticleList>>().await()
    }

    suspend fun getHomeProjectList(page: Int): BaseResponse<HomeArticleList> {
        return getRequest { url(C.BASE_URL + "/article/listproject/$page/json") }.toClazz<BaseResponse<HomeArticleList>>().await()
    }
}