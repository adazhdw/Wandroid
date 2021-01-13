package com.zhdw.wandroid.ui.square

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.base.mvvm.viewModel
import com.adazhdw.ktlib.ext.toast
import com.adazhdw.ktlib.ext.view.setTextSizeSp
import com.adazhdw.ktlib.list.adapter.ViewBindingAdapter
import com.adazhdw.ktlib.list.holder.BaseVBViewHolder
import com.google.android.material.chip.Chip
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentNavigationBinding
import com.zhdw.wandroid.databinding.ItemNavClassListBinding
import com.zhdw.wandroid.ui.vm.SquareViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter


/**
 * Administrator
 * create at 2020/4/9 16:11
 * description:
 */
class NavFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentNavigationBinding
    private val mNavListAdapter by lazy { NavListAdapter() }
    private val viewModel by lazy { viewModel<SquareViewModel>() }
    private val mLayoutInflaterNav by lazy { LayoutInflater.from(context) }

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentNavigationBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView(view: View) {
        viewBinding.navRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.navRV.adapter = mNavListAdapter
    }

    override fun initData() {
        viewModel.mNaviData.observe(this, Observer {
            mNavListAdapter.addData(it)
        })
    }

    override fun requestStart() {
        viewModel.getNaviData()
    }

    inner class NavListAdapter : ViewBindingAdapter<NaviData>() {

        private fun getChip(context: Context, naviClassData: NaviClassData): Chip {
            return Chip(context).apply {
                text = naviClassData.title
                setTextSizeSp(14f)
                setOnClickListener { toast(naviClassData.title) }
            }
        }

        override fun viewBinding(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): ViewBinding {
            return ItemNavClassListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        override fun notifyBind(holder: BaseVBViewHolder, data: NaviData, position: Int) {
            (holder.viewBinding as ItemNavClassListBinding).let {
                it.navItemTitle.text = data.name
                it.flowLayout.adapter = object : TagAdapter<NaviClassData>(data.articles) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        t: NaviClassData?
                    ): View {
                        val tv: TextView = mLayoutInflaterNav.inflate(
                            R.layout.item_tag_layout,
                            parent,
                            false
                        ) as TextView
                        tv.text = data.articles[position].title
                        return tv
                    }
                }
                it.flowLayout.setOnTagClickListener { _, position, _ ->
                    toast(data.articles[position].title)
                    true
                }
            }
        }

    }

}
