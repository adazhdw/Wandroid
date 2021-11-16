package com.zhdw.wandroid.ui.vm

import androidx.lifecycle.MutableLiveData
import com.adazhdw.kthttp.coroutines.toClazz
import com.adazhdw.kthttp.getRequest
import com.adazhdw.ktlib.base.mvvm.BaseRepository
import com.adazhdw.ktlib.base.mvvm.BaseViewModel
import com.google.gson.reflect.TypeToken
import com.zhdw.wandroid.constant.C
import com.zhdw.wandroid.http.BaseResponse
import com.zhdw.wandroid.ui.square.TreeData
import com.zhdw.wandroid.utils.*


/**
 * Administrator
 * create at 2020/4/13 15:54
 * description:
 */
class TreeTabViewModel : BaseViewModel<WxSubRepository>() {
    override fun obtainRepository(): WxSubRepository = WxSubRepository()

    val mWxSubScription: MutableLiveData<List<TreeData>> = MutableLiveData()
    val mProjectTreeData: MutableLiveData<List<TreeData>> = MutableLiveData()

    fun getWxSub() {//WEIXIN_TREE_TAB
        launch {
            val treeJson = spString(WEIXIN_TREE_TAB)
            if (treeJson.isNotBlank()) {
                try {
                    val data: List<TreeData> = GsonUtil.gson.fromJson(
                            treeJson,
                            object : TypeToken<List<TreeData>>() {}.type
                    )
                    mWxSubScription.postValue(data)
                } catch (e: Exception) {
                    val data = mRepository.geWxSub().data ?: return@launch
                    spStringSave(WEIXIN_TREE_TAB, GsonUtil.toJson(data))
                    mWxSubScription.postValue(data)
                }
            } else {
                val data = mRepository.geWxSub().data ?: return@launch
                spStringSave(WEIXIN_TREE_TAB, GsonUtil.toJson(data))
                mWxSubScription.postValue(data)
            }
        }
    }

    fun getProjectTreeData() {
        launch {
            val treeJson = spString(PROJECT_TREE_TAB)
            if (treeJson.isNotBlank()) {
                try {
                    val data: List<TreeData> = GsonUtil.gson.fromJson(
                            treeJson,
                            object : TypeToken<List<TreeData>>() {}.type
                    )
                    mProjectTreeData.postValue(data)
                } catch (e: Exception) {
                    val data = mRepository.getProjectTreeData().data ?: return@launch
                    spStringSave(PROJECT_TREE_TAB, GsonUtil.toJson(data))
                    mProjectTreeData.postValue(data)
                }
            } else {
                val data = mRepository.getProjectTreeData().data ?: return@launch
                spStringSave(PROJECT_TREE_TAB, GsonUtil.toJson(data))
                mProjectTreeData.postValue(data)
            }
        }
    }

}


class WxSubRepository : BaseRepository() {

    suspend fun geWxSub(): BaseResponse<List<TreeData>> {
        return getRequest { url(C.BASE_URL + "/wxarticle/chapters/json") }.toClazz<BaseResponse<List<TreeData>>>().await()
    }

    suspend fun getProjectTreeData(): BaseResponse<List<TreeData>> {
        return getRequest { url(C.BASE_URL + "/project/tree/json") }.toClazz<BaseResponse<List<TreeData>>>().await()
    }

}