package com.zhdw.wandroid.ui

/**
 * Created by daguozhu
 * at: 2020/3/10 14:20.
 * desc:
 */

data class BannerData(
    val imagePath: String = "",
    val title: String = "",
    val url: String = ""
)

data class ArticleData(
    val apkLink: String = "",
    val audit: Int = 0,
    val author: String = "",//作者
    val canEdit: Boolean = false,
    val chapterId: Long = 0,
    val chapterName: String = "",
    val collect: Boolean = false,
    val courseId: Long = 0,
    val desc: String = "",
    val descMd: String = "",
    val envelopePic: String = "",
    val fresh: Boolean = false,
    val id: Int = 0,//文章id
    val link: String = "",//文章链接
    val niceDate: String = "",//中文时间
    val niceShareDate: String = "",//格式化时间
    val origin: String = "",
    val prefix: String = "",
    val projectLink: String = "",
    val publishTime: Long = 0,
    val selfVisible: Int = 0,
    val shareDate: Long = 0,//分享时间
    val shareUser: String = "",//分享人
    val superChapterId: Long = 0,
    val superChapterName: String = "",
    val tags: List<ArticleTag> = listOf(),
    val title: String = "",//文章标题
    val type: Int = 0,
    val userId: Long = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    var isPinTop: Boolean = false//是否置顶
)

data class ArticleTag(
    val name: String = "",
    val url: String = ""
)

data class HomeArticleList(
    val curPage: Int = 0,
    val datas: List<ArticleData> = listOf(),
    val size: Int = 0,
    val total: Int = 0
)

data class FriendWeb(
    val id: Long = 0L,
    val link: String = "",
    val name: String = ""
)
