package com.zhdw.wandroid.ui.info

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.zhdw.wandroid.base.BaseActivityBinding
import com.zhdw.wandroid.databinding.ActivityArticleListBinding
import com.zhdw.wandroid.ui.startActivity


/**
 * dgz
 * create at 2020/5/6 13:40
 * description:
 */
class ArticleListActivity : BaseActivityBinding() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity<ArticleListActivity>()
        }
    }

    private lateinit var viewBinding: ActivityArticleListBinding
    override fun initViewBinding(): ViewBinding {
        viewBinding = ActivityArticleListBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView() {
        viewBinding.toolbar.setNavigationOnClickListener { finish() }

    }

}