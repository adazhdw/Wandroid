package com.zhdw.wandroid

import com.adazhdw.ktlib.Application
import com.adazhdw.ktlib.initLibrary
import com.zhdw.wandroid.constant.C


/**
 * Administrator
 * create at 2020/4/2 17:24
 * description:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLibrary(C.BASE_URL, true)
    }
}