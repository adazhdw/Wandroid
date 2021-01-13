package com.zhdw.wandroid.ui.square

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.ViewPager
import com.adazhdw.ktlib.ext.dp2px
import com.adazhdw.ktlib.ext.getColorEx
import com.zhdw.wandroid.R
import com.zhdw.wandroid.base.BaseFragmentBinding
import com.zhdw.wandroid.databinding.FragmentSquareBinding
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
class SquareFragment : BaseFragmentBinding() {

    private lateinit var viewBinding: FragmentSquareBinding
    private val titleArray = arrayOf("最新文章", "最新项目", "广场", "导航", "体系")

    override fun initViewBinding(): ViewBinding {
        viewBinding = FragmentSquareBinding.inflate(layoutInflater)
        return viewBinding
    }

    override fun initView(view: View) {
        val fragments = listOf<Fragment>(
            NewestArticlesFragment(),
            NewestProjectsFragment(),
            PlazaFragment2(),
            NavFragment(),
            TreeFragment()
        )
        val vpAdapter = SquareFragAdapter(childFragmentManager, fragments)
        viewBinding.squareVP2.adapter = vpAdapter

        val indicator = viewBinding.magicIndicator
        val commonNavigator = CommonNavigator(view.context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val title = ClipPagerTitleView(context)
                title.text = titleArray[index]
                title.textColor = getColorEx(R.color.colorGray)
                title.clipColor = getColorEx(R.color.white)
                title.textSize = dp2px(16f).toFloat()
                title.setPadding(dp2px(20f), 0, dp2px(10f), 0)
                title.setOnClickListener { viewBinding.squareVP2.currentItem = index }
                return title
            }

            override fun getCount(): Int = fragments.size

            override fun getIndicator(context: Context?): IPagerIndicator? = null
        }
        indicator.navigator = commonNavigator
        viewBinding.squareVP2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
    }
}