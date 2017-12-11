package com.uni.phamduy.massagefinder

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by PhamDuy on 9/12/2017.
 */
class MoveScreen(internal var context: Context) {
    private val KEY_ID = "ID"

    fun clickedOn(idlayout: Int, fragment: Fragment) {
        val tag = fragment.javaClass.toString()
        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .addToBackStack(null)
                .replace(idlayout, fragment)
                .commit()
    }

    fun moveOneFragment(idlayout: Int, fragment: Fragment, bundle: Bundle) {

        fragment.arguments = bundle
        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(null)
                .replace(idlayout, fragment)
                .commit()

    }

    fun moveTwoFragment(idlayout1: Int, idlayout2: Int, fragment1: Fragment, fragment2: Fragment, bundle: Bundle) {
        fragment1.arguments = bundle
        fragment2.arguments = bundle
        (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(null)
                .replace(idlayout1, fragment1)
                .replace(idlayout2, fragment2)
                .commit()

    }

    fun finishFragment(fragment: Fragment) {
        //        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        (context as FragmentActivity).supportFragmentManager.popBackStack()
    }
}
