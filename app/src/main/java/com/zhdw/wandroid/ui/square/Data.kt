package com.zhdw.wandroid.ui.square


/**
 * Administrator
 * create at 2020/4/13 11:09
 * description:
 */

class NaviData(
    val articles: List<NaviClassData> = listOf(),
    val cid: Long = 0,
    val name: String = ""
)

class NaviClassData(
    val chapterId: Long = 0,
    val chapterName: String = "",
    val link: String = "",
    val title: String = "",
    val id: Long = 0
)


class TreeData(
    val children: List<TreeData> = listOf(),
    val courseId: Long = 0,
    val id: Long = 0,
    val name: String = "",
    val order: Long = 0,
    val parentChapterId: Long = 0
)