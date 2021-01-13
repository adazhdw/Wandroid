package com.zhdw.wandroid.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.youth.banner.adapter.BannerAdapter
import com.zhdw.wandroid.R
import com.zhdw.wandroid.ui.glide
import com.zhdw.wandroid.ui.BannerData


/**
 * Administrator
 * create at 2020/4/9 13:57
 * description:
 */


class HomeBannerAdapter(private val context: Context?) :
    BannerAdapter<BannerData, BaseViewHolder>(mutableListOf()) {
    private val layoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return BaseViewHolder(layoutInflater.inflate(R.layout.banner_item, parent, false))
    }

    override fun onBindView(holder: BaseViewHolder?, data: BannerData?, position: Int, size: Int) {
        holder?.let { h ->
            data.let {
                h.getView<ImageView>(R.id.bannerIv).glide(data?.imagePath)
            }
        }
    }
}
