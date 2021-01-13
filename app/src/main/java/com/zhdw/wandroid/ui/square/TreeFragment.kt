package com.zhdw.wandroid.ui.square

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.adazhdw.ktlib.ext.toast
import com.adazhdw.ktlib.list.adapter.ViewBindingAdapter
import com.adazhdw.ktlib.list.holder.BaseVBViewHolder
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentTreeBinding
import com.zhdw.wandroid.databinding.ItemTreeListBinding
import com.zhdw.wandroid.ui.vm.SquareViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter


/**
 * Administrator
 * create at 2020/4/9 16:12
 * description:
 */
class TreeFragment : BaseFragmentBinding() {
    private lateinit var viewBinding: FragmentTreeBinding
    private val viewModel by lazy { SquareViewModel() }
    private val adapter by lazy { TreeAdapter() }

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentTreeBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView(view: View) {
        viewBinding.treeRV.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.treeRV.adapter = adapter
    }

    override fun initData() {
        viewModel.mTreeData.observe(this, Observer {
            adapter.addData(it)
        })
    }

    override fun requestStart() {
        viewModel.getTreeData()
    }


    inner class TreeAdapter : ViewBindingAdapter<TreeData>() {

        override fun viewBinding(parent: ViewGroup, inflater: LayoutInflater, viewType: Int): ViewBinding {
            return ItemTreeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        override fun notifyBind(holder: BaseVBViewHolder, data: TreeData, position: Int) {
            (holder.viewBinding as ItemTreeListBinding).let {
                it.navItemTitle.text = data.name
                it.flowLayout.adapter = object : TagAdapter<TreeData>(data.children) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        treeData: TreeData?
                    ): View {
                        val tv: TextView = layoutInflater.inflate(
                            R.layout.item_tag_layout,
                            parent,
                            false
                        ) as TextView
                        tv.text = treeData?.name
                        return tv
                    }
                }
                it.flowLayout.setOnTagClickListener { _, position, _ ->
                    toast(data.children[position].name)
                    true
                }
            }
        }
    }

}
