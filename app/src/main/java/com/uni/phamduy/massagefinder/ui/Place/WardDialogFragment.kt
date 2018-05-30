package com.uni.phamduy.massagefinder.ui.Place

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.WardAdapter
import com.uni.phamduy.massagefinder.ui.Map.MapFragment
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
        val ward_array = activity.resources.getStringArray(R.array.city)
        val arrayWard :List<String> = ward_array.toList()
        rvDialog = v!!.findViewById(R.id.rvDialog)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDialog!!.layoutManager = linearLayoutManager
         adapter = WardAdapter(context, arrayWard, WardFragment.instance.posWard!!)
        rvDialog!!.adapter = adapter
        adapter!!.setOnItemClickListener(object : WardAdapter.ClickListener  {
            override fun onItemClick(position: Int, v: View) {
                when(position){
                    0-> {

                        MapFragment.City.mCity="ho-chi-minh"
                        dialog.dismiss()
                        WardFragment.instance.chooseWard(0)
                    }
                    1-> {
                        MapFragment.City.mCity = "ha-noi"
                        dialog.dismiss()
                        WardFragment.instance.chooseWard(1)
                    }
                    2-> {
                        MapFragment.City.mCity="da-nang"
                        dialog.dismiss()
                        WardFragment.instance.chooseWard(2)
                    }
                }
            }


        })
        return v
    }


}