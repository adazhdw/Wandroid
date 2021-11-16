package com.zhdw.wandroid.ui.web

import android.content.Context
import android.graphics.Bitmap
import android.view.KeyEvent
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.ext.getColorEx
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseActivityBinding
import com.zhdw.wandroid.databinding.ActivityWebviewLayoutBinding
import com.zhdw.wandroid.ui.startActivity


/**
 * Administrator
 * create at 2020/4/21 13:25
 * description:
 */

fun Context.startWeb(url: String) {
    if (url.isBlank()) return
    startActivity<WebViewActivity>("url" to url)
}

class WebViewActivity : BaseActivityBinding() {

    private lateinit var viewBinding: ActivityWebviewLayoutBinding
    private var mAgentWeb: AgentWeb? = null

    override fun initViewBinding(): ViewBinding {
        viewBinding = ActivityWebviewLayoutBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView() {
        viewBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        viewBinding.toolbar.setTitleTextColor(getColorEx(R.color.white))
        viewBinding.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun initData() {
        val url = intent.getStringExtra("url")
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(viewBinding.container, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb?.handleKeyEvent(keyCode, event) == true) {
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //do you  work
            viewBinding.toolbar.title = view?.title
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            //do you work
            viewBinding.toolbar.title = view?.title
        }
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}