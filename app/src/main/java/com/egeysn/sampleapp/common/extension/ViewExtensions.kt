package com.egeysn.sampleapp.common.extension

import android.view.View

fun View?.show() {
    if (this == null) return
    visibility = View.VISIBLE
}

fun View?.hide() {
    if (this == null) return
    visibility = View.GONE
}
