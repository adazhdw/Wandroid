package com.zhdw.wandroid.ui.vm

import androidx.lifecycle.MutableLiveData
import com.adazhdw.kthttp.coroutines.toClazz
import com.adazhdw.kthttp.getRequest
import com.adazhdw.ktlib.base.mvvm.BaseRepository
import com.adazhdw.ktlib.base.mvvm.BaseViewModel
import com.zhdw.wandroid.constant.C
import com.zhdw.wandroid.http.BaseResponse
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.HomeArticleList
import com.zhdw.wandroid.ui.square.NaviData
import com.zhdw.wandroid.ui.square.TreeData


/**
 * Administrator
 * create at 2020/4/10 10:44
 * description:
 */
class SquareViewModel : BaseViewModel<SquareRepository>() {
    override fun obtainRepository(): SquareRepository {
        return SquareRepository()
    }

    val plazaList: MutableLiveData<List<ArticleData>> = MutableLiveData()
    val mNaviData: MutableLiveData<List<NaviData>> = MutableLiveData()
    val mTreeData: MutableLiveData<List<TreeData>> = MutableLiveData()

    fun getPlazaList(page: Int) {
        launch {
            val data = mRepository.getPlazaList(page).data?.datas ?: return@launch
            plazaList.postValue(data)
        }
    }

    fun getNaviData() {
        launch {
            val data = mRepository.getNaviData().data ?: return@launch
            mNaviData.postValue(data)
        }
    }

    fun getTreeData() {
        launch {
            val data = mRepository.getTreeData().data ?: return@launch
            mTreeData.postValue(data)
        }
    }

}

class SquareRepository : BaseRepository() {

    suspend fun getPlazaList(page: Int): BaseResponse<HomeArticleList> {
        return getRequest { url(C.BASE_URL + "/user_article/list/$page/json") }.toClazz<BaseResponse<HomeArticleList>>().await()
    }

    suspend fun getNaviData(): BaseResponse<List<NaviData>> {
        return getRequest { url(C.BASE_URL + "/navi/json") }.toClazz<BaseResponse<List<NaviData>>>().await()
    }

    suspend fun getTreeData(): BaseResponse<List<TreeData>> {
        return getRequest { url(C.BASE_URL + "/tree/json") }.toClazz<BaseResponse<List<TreeData>>>().await()
    }
}