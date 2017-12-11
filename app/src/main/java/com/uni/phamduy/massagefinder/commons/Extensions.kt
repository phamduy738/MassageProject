@file:JvmName("ExtensionsUtils")
package com.uni.phamduy.massagefinder.commons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by PhamDuy on 9/21/2017.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View{
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
