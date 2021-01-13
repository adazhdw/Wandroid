package com.zhdw.wandroid.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.adazhdw.ktlib.R
import com.adazhdw.ktlib.ext.putExtrasEx
import com.adazhdw.ktlib.ext.transact
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


/**
 * Administrator
 * create at 2020/4/2 16:16
 * description:
 */


fun FragmentActivity.hideFragment(
    fragment: Fragment,
    isAllowingStateLose: Boolean = false
) {
    supportFragmentManager.transact(isAllowingStateLose) {
        hide(fragment)
    }
}

fun ImageView.glide(url: String?, @DrawableRes placeholder: Int? = null) {
    Glide.with(context).clear(this)
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(placeholder ?: R.drawable.bg_iv_default)
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions().crossFade())
        .apply(options)
        .into(this)
}

fun NestedScrollView.onScroll(
    bottomScroll: (() -> Unit)? = null,
    topScroll: (() -> Unit)? = null,
    scrollUp: (() -> Unit)? = null,
    scrollDown: (() -> Unit)? = null
) {
    setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
        if (scrollY > oldScrollY) {
            scrollDown?.invoke()
        }
        if (scrollY < oldScrollY) {
            scrollUp?.invoke()
        }
        if (scrollY == 0) {
            topScroll?.invoke()
        }
        if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
            bottomScroll?.invoke()
        }
    })
}

inline fun <reified T : Activity> Context?.startActivity(vararg extras: Pair<String, Any?>) {
    this?.startActivity(Intent(this, T::class.java).putExtrasEx(*extras))
}
