package com.uni.phamduy.massagefinder.ui.Staff

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uni.phamduy.massagefinder.MainActivity
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListStaffAdapter
import com.uni.phamduy.massagefinder.ui.ListReviewActivity
import java.util.*

/**
 * Created by PhamDuy on 9/13/2017.
 */
class StaffFragment: Fragment() {
    private lateinit var rvStaff: RecyclerView
    private var staffAdapter: ListStaffAdapter? = null
    private var listStaff: MutableList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_staff, container, false)
        MainActivity.instance.chooseBottomView(3)
        rvStaff = view!!.findViewById(R.id.rvStaff)
        init()

        return view
    }

    fun init() {
        listStaff.clear()
        addList()
        staffAdapter = ListStaffAdapter(activity, listStaff)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvStaff.adapter = staffAdapter
        rvStaff.layoutManager = linearLayoutManager
        staffAdapter!!.setOnItemClickListener(object : ListStaffAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {
                val intent = Intent(context, ListReviewActivity::class.java)
                context.startActivity(intent)
            }

        })
    }
    fun addList() {
        for (i in 0..20) {
            listStaff.add(i.toString())
        }
    }
}