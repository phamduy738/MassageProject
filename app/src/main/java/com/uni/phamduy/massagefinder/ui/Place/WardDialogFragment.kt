package com.uni.phamduy.massagefinder.ui.Place

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.WardAdapter
import java.util.*

/**
 * Created by PhamDuy on 9/19/2017.
 */
class WardDialogFragment : DialogFragment() {
    private var rvDialog: RecyclerView? = null
    private var adapter: WardAdapter? = null
//    var customList:List<Arrays>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_ward_dialog, container, false)
         var customList = Arrays.asList(activity.resources.getStringArray(R.array.ward))
        val ward_array = activity.resources.getStringArray(R.array.city)
        val arrayWard :List<String> = ward_array.toList()
        val sort_array = activity.resources.getStringArray(R.array.sort)
        val arraySort :List<String> = sort_array.toList()
        rvDialog = v?.findViewById(R.id.rvDialog)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDialog!!.layoutManager = linearLayoutManager
        //setadapter
         adapter = WardAdapter(context, arrayWard)
        rvDialog!!.adapter = adapter
        adapter!!.setOnItemClickListener(object : WardAdapter.ClickListener  {
            override fun onItemClick(position: Int, v: View) {
                dialog.dismiss()
            }


        })
        return v
    }
}