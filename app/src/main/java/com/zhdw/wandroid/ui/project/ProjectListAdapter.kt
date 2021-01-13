package com.zhdw.wandroid.ui.project

import android.text.Html
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.ext.view.visible
import com.adazhdw.ktlib.list.adapter.ViewBindingAdapter
import com.adazhdw.ktlib.list.holder.BaseVBViewHolder
import com.zhdw.wandroid.databinding.ItemProjectListBinding
import com.zhdw.wandroid.ui.ArticleData
import com.zhdw.wandroid.ui.glide


/**
 * Administrator
 * create at 2020/4/9 13:58
 * description:
 */

class ProjectListAdapter : ViewBindingAdapter<ArticleData>() {

    private fun getAuthorString(data: ArticleData): SpannableStringBuilder {
        val string = SpannableStringBuilder()
        val author = if (data.author.isNotBlank()) data.author else data.shareUser
        string.append("$author，")
        string.append(data.superChapterName + "/" + data.chapterName)
        return string
    }

    override fun viewBinding(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): ViewBinding {
        return ItemProjectListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun notifyBind(holder: BaseVBViewHolder, data: ArticleData, position: Int) {
        (holder.viewBinding as ItemProjectListBinding).let {
            it.projectImg.glide(data.envelopePic)
            it.articleTitleTv.text = Html.fromHtml(data.title)
            it.pinTopTag.isVisible = data.isPinTop
            it.newestTag.isVisible = data.fresh
            it.authorTv.text = getAuthorString(data)
            it.timeTv.text = data.niceDate
            if (data.tags.size == 1) {
                it.publishTag.visible()
                it.publishTag.text = data.tags[0].name
            } else if (data.tags.size == 2) {
                it.publishTag.visible()
                it.qATag.visible()
                it.publishTag.text = data.tags[0].name
                it.qATag.text = data.tags[1].name
            }
        }
    }
}