package com.zhdw.wandroid.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


/**
 * Administrator
 * create at 2020/4/14 9:23
 * description:
 */

object GsonUtil {

    val gson: Gson by lazy { GsonBuilder().create() }

    inline fun <reified T> fromJson(json: String): T {
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }

    inline fun <reified T> fromJsonList(json: String): List<T> {
        return gson.fromJson(json, object : TypeToken<List<T>>() {}.type)
    }

    fun toJson(any: Any): String? {
        return gson.toJson(any)
    }
}