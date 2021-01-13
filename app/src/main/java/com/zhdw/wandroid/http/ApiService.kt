package com.zhdw.wandroid.http

import com.zhdw.wandroid.ui.BannerData
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.FriendWeb
import com.zhdw.wandroid.ui.HomeArticleList
import com.zhdw.wandroid.ui.search.HotKey
import com.zhdw.wandroid.ui.square.NaviData
import com.zhdw.wandroid.ui.square.TreeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Administrator
 * create at 2020/4/2 17:21
 * description:
 */

class BaseResponse<T>(
    val data: T? = null,
    val errorCode: String = "",
    val errorMsg: String = ""
) {
    val isSuccess: Boolean
        get() = errorCode == "0"
}

class ListResponse<T>(
    val data: List<T>? = null,
    val errorCode: String = "",
    val errorMsg: String = ""
) {
    val isSuccess: Boolean
        get() = errorCode == "0"
}
