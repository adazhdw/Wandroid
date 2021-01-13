package com.zhdw.wandroid.widget

import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView


/**
 * Administrator
 * create at 2020/4/24 9:22
 * description:
 */

fun TagContainerLayout.onTagClick(click: (position: Int) -> Unit) {
    this.setOnTagClickListener(object : TagView.OnTagClickListener {
        override fun onSelectedTagDrag(position: Int, text: String?) {

        }

        override fun onTagLongClick(position: Int, text: String?) {

        }

        override fun onTagClick(position: Int, text: String?) {
            click(position)
        }

        override fun onTagCrossClick(position: Int) {

        }
    })
}

