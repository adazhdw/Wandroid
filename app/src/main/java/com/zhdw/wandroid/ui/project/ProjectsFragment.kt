package com.zhdw.wandroid.ui.project

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.ViewPager
import com.adazhdw.ktlib.ext.dp2px
import com.adazhdw.ktlib.ext.getColorEx
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentProjectsBinding
import com.zhdw.wandroid.ui.square.SquareFragAdapter
import com.zhdw.wandroid.ui.vm.TreeTabViewModel
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

/**
 * Created by daguozhu
 * at: 2020/3/10 14:21.
 * desc:
 */
class ProjectsFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentProjectsBinding

    private lateinit var vpAdapter: SquareFragAdapter
    private val fragments = mutableListOf<Fragment>()
    private val titleArray = mutableListOf<String>()
    private val viewModel by lazy { TreeTabViewModel() }

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentProjectsBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initData() {
        viewModel.mProjectTreeData.observe(this, Observer {
            for (sub in it) {
                titleArray.add(sub.name)
                fragments.add(ProjectListFragment.instance(sub.id))
            }

            vpAdapter = SquareFragAdapter(
                    childFragmentManager,
                    fragments
            )
            viewBinding.projectVP.adapter = vpAdapter

            val indicator = viewBinding.magicIndicator
            val commonNavigator = CommonNavigator(mContext)
            commonNavigator.adapter = object : CommonNavigatorAdapter() {
                override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                    val title = ClipPagerTitleView(context)
                    title.text = titleArray[index]
                    title.textColor = getColorEx(R.color.colorGray)
                    title.clipColor = getColorEx(R.color.white)
                    title.textSize = dp2px(16f).toFloat()
                    title.setPadding(dp2px(20f), 0, dp2px(10f), 0)
                    title.setOnClickListener { viewBinding.projectVP.currentItem = index }
                    return title
                }

                override fun getCount(): Int = fragments.size

                override fun getIndicator(context: Context?): IPagerIndicator? = null
            }
            indicator.navigator = commonNavigator
            viewBinding.projectVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    indicator.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                ) {
                    indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    indicator.onPageSelected(position)
                }
            })
            vpAdapter.notifyDataSetChanged()
        })
    }

    override fun requestStart() {
        viewModel.getProjectTreeData()
    }
}