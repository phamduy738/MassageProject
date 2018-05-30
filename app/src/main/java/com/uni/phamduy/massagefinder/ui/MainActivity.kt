package com.uni.phamduy.massagefinder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration
import com.uni.phamduy.massagefinder.ui.Map.MapFragment
import com.uni.phamduy.massagefinder.ui.Place.WardFragment
import com.uni.phamduy.massagefinder.ui.Staff.StaffFragment
import com.uni.phamduy.massagefinder.utils.Common
import kotlinx.android.synthetic.main.main_activity.*


/**
 * Created by PhamDuy on 9/8/2017.
 */
class MainActivity : AppCompatActivity() {
    lateinit var tv_title: TextView
    lateinit var rlFilterCity:RelativeLayout
    lateinit var rlSort:RelativeLayout
    lateinit var ed_search: EditText
    lateinit var search_layout: ConstraintLayout
    lateinit var imgFilter: ImageView
    lateinit var toolbar: Toolbar
    private var moveScreen: MoveScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        init()
        setFirstFragment()
        setupUI(findViewById(R.id.main_layout))

        //set listener bottom
        with(bottom_navigation) {
            setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }
    }

    fun chooseBottomView(i: Int) {
        if (i == 1) {
            bottom_navigation!!.menu.findItem(R.id.navigation_home).isChecked = true
        }
        if (i == 2) {
            bottom_navigation!!.menu.findItem(R.id.navigation_dashboard).isChecked = true
        }
        if (i == 3) {
            bottom_navigation!!.menu.findItem(R.id.navigation_notifications).isChecked = true
        }
    }

    fun init() {
        instance = this
        tv_title = findViewById(R.id.tv_title)
        rlFilterCity = findViewById(R.id.rlFilterCity)
        rlSort = findViewById(R.id.rlSort)
        ed_search = findViewById(R.id.ed_search)
        search_layout = findViewById(R.id.search_layout)
        imgFilter = findViewById(R.id.imgFilter)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    private fun setFirstFragment() {
        moveScreen = MoveScreen(this@MainActivity)
        moveScreen!!.clickedOn(R.id.content, MapFragment())
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (!item.isChecked) {
                    moveScreen!!.clickedOn(R.id.content, MapFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                if (!item.isChecked) {
                    moveScreen!!.clickedOn(R.id.content, WardFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                if (!item.isChecked) {
                    moveScreen!!.clickedOn(R.id.content, StaffFragment())
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    // singleton
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MainActivity
            internal set
    }


    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    //
    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(object : OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    hideKeyboard(v)
                    return false
                }
            })
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0..(view ).childCount - 1) {
                val innerView = (view).getChildAt(i)
                setupUI(innerView)
            }
        }
    }
    fun showProgressDialog(context: Context): ProgressDialog {
        val pDialog = ProgressDialog(context)
        pDialog.setMessage("Please wait...")
        pDialog.setCancelable(false)
        pDialog.show()
        return pDialog
    }

    fun hideProgressDialog(pDialog: ProgressDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss()
    }

}