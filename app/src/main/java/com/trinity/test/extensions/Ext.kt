@file:Suppress("DEPRECATION")

package com.trinity.test.extensions

import android.graphics.PorterDuff
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.trinity.test.R

fun ImageView.changeToOrange(icon: Int) {
    val ic = ContextCompat.getDrawable(context, icon)
    ic?.setColorFilter(
        ContextCompat.getColor(context, R.color.colorPrincetonOrange),
        PorterDuff.Mode.SRC_ATOP
    )
    this.setImageDrawable(ic)
}

fun tryCatch(listener: () -> Unit) {
    try {
        listener()
    } catch (e: Exception) {
       Log.e("error:","${e.message}")
    }
}

fun <T> T.appLog(msg: String = ""): T {
    Log.e("error:", "$msg ")
    return this
}