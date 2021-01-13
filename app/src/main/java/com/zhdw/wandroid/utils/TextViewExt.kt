package com.zhdw.wandroid.utils

import android.os.Build
import android.widget.TextView
import androidx.annotation.StyleRes


/**
 * Administrator
 * create at 2020/4/8 14:16
 * description:
 */

fun TextView.setStyleEx(@StyleRes res: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(res)
    } else {
        setTextAppearance(this.context, res)
    }
}
