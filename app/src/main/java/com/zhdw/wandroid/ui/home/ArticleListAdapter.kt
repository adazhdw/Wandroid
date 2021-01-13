package com.zhdw.wandroid.ui.home

import android.graphics.Color
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.ext.view.invisible
import com.adazhdw.ktlib.ext.view.visible
import com.adazhdw.ktlib.list.adapter.ViewBindingAdapter
import com.adazhdw.ktlib.list.holder.BaseVBViewHolder
import com.zhdw.wandroid.databinding.ItemHomeArticleListBinding
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.web.startWeb


/**
 * Administrator
 * create at 2020/4/9 13:58
 * description:
 */

class ArticleListAdapter : ViewBindingAdapter<ArticleData>() {

    private fun getAuthorString(data: ArticleData): SpannableStringBuilder {
        val string = SpannableStringBuilder()
        string.append("作者：")
        val author = if (data.author.isNotBlank()) data.author else data.shareUser
        string.append(author)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#555555"))
        string.setSpan(colorSpan, 3, 3 + author.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

    private fun getTypeString(data: ArticleData): SpannableStringBuilder {
        val string = SpannableStringBuilder()
        string.append("分类：")
        val type = data.superChapterName + "/" + data.chapterName
        string.append(type)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#555555"))
        string.setSpan(colorSpan, 3, 3 + type.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

    override fun viewBinding(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): ViewBinding {
        return ItemHomeArticleListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun notifyBind(holder: BaseVBViewHolder, data: ArticleData, position: Int) {
        (holder.viewBinding as ItemHomeArticleListBinding).let {
            it.articleTitleTv.text = Html.fromHtml(data.title)
            it.pinTopTag.isVisible = data.isPinTop
            it.newestTag.isVisible = data.fresh
            it.authorTv.text = getAuthorString(data)
            it.typeTv.text = getTypeString(data)
            it.timeTv.text = data.niceDate
            when (data.tags.size) {
                1 -> {
                    it.publishTag.visible()
                    it.publishTag.text = data.tags[0].name
                }
                2 -> {
                    it.publishTag.visible()
                    it.qATag.visible()
                    it.publishTag.text = data.tags[0].name
                    it.qATag.text = data.tags[1].name
                }
                else -> {
                    it.publishTag.invisible()
                    it.qATag.invisible()
                }
            }
            it.root.setOnClickListener { _ ->
                it.root.context.startWeb(data.link)
            }
        }
    }
}