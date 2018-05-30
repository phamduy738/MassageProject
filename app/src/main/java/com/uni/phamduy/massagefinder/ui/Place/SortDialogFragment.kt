package com.uni.phamduy.massagefinder.ui.Place

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.SortAdapter
import com.uni.phamduy.massagefinder.adapter.WardAdapter
import com.uni.phamduy.massagefinder.ui.Map.MapFragment
import java.util.*

/**
 * Created by PhamDuy on 9/19/2017.
 */
class SortDialogFragment : DialogFragment() {
    private var rvDialog: RecyclerView? = null
    private var adapter: SortAdapter? = null
//    var customList:List<Arrays>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_ward_dialog, container, false)
        val sort_array = activity.resources.getStringArray(R.array.sort)
        val arraySort :List<String> = sort_array.toList()
        rvDialog = v?.findViewById(R.id.rvDialog)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDialog!!.layoutManager = linearLayoutManager

         adapter = SortAdapter(context, arraySort, PlaceFragment.instance.posSort!!)
        rvDialog!!.adapter = adapter
        adapter!!.setOnItemClickListener(object : SortAdapter.ClickListener {
            override fun onItemClick(position: Int, v: View) {
                when (position) {
                    0 -> {

                        MapFragment.Sort.mSort = "distance"
                        dialog.dismiss()
                        PlaceFragment.instance.chooseSoort(0)

                    }
                    1 -> {
                        MapFragment.Sort.mSort = "rating"
                        dialog.dismiss()
                        PlaceFragment.instance.chooseSoort(1)

                    }
                    2 -> {
                        MapFragment.Sort.mSort = "alphabet"
                        dialog.dismiss()
                        PlaceFragment.instance.chooseSoort(2)
                    }
                }
            }

        })
        return v
    }
}