package com.zhdw.wandroid.utils

import com.adazhdw.ktlib.KtLib
import com.adazhdw.ktlib.ext.spGetValue
import com.adazhdw.ktlib.ext.spPutValue


/**
 * Administrator
 * create at 2020/4/14 9:10
 * description:
 */

const val spName = "Wandroid_sharepreference"
const val PROJECT_TREE_TAB = "project_tree_tab"
const val WEIXIN_TREE_TAB = "weixin_tree_tab"

fun spLong(param: String): Long {
    return KtLib.context.spGetValue(param, 0L)
}

fun spInt(param: String): Int {
    return KtLib.context.spGetValue(param, 0)
}

fun spString(param: String): String {
    return KtLib.context.spGetValue(param, "")
}

fun spStringSave(param: String, value: String?) {
    KtLib.context.spPutValue(param, value)
}