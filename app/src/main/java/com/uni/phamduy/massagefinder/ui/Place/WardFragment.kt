package com.uni.phamduy.massagefinder.ui.Place

import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.uni.phamduy.coinmarket.networking.ApiUtils
import com.uni.phamduy.coinmarket.networking.SOService
import com.uni.phamduy.massagefinder.MainActivity
import com.uni.phamduy.massagefinder.MoveScreen
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListWardAdapter
import com.uni.phamduy.massagefinder.adapter.SearchAdapter
import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.module.place.Place
import com.uni.phamduy.massagefinder.ui.Map.MapFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * Created by PhamDuy on 9/13/2017.
 */
class WardFragment : Fragment(), View.OnClickListener {


    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.imgFilter -> {
                    val fm = fragmentManager
                    val wardDialog = WardDialogFragment()

                        wardDialog.retainInstance = true
                        wardDialog.show(fm, "fragment_ward")
                        isShow = false
                }

            }
        }
    }

    private lateinit var rvWard: RecyclerView
    var storeAdapter: ListWardAdapter? = null
    var listWard: MutableList<District> = ArrayList()
    private var searchAdapter: SearchAdapter? = null
    private var listFilter: MutableList<String> = ArrayList()
    private var moveScreen: MoveScreen? = null
    var service: SOService? = null
    private var city: String? = null
    val offset = 10
    var isFilter: Boolean = true
    var isShow: Boolean = true
    var posWard: Int? = 0


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_list_ward, container, false)
        instance = this
        rvWard = view!!.findViewById(R.id.rvPlace)
        service = ApiUtils.soService
        setHeader()
        init()
        MainActivity.instance.imgFilter.setOnClickListener(this)

        return view
    }


    companion object {
        lateinit var instance: WardFragment
            private set
    }

    fun chooseWard(p: Int) {
        rvWard?.scrollToPosition(0)
        posWard = p
        listWard.clear()
        addList()
    }

    fun init() {

        addList()
        storeAdapter = ListWardAdapter(activity, listWard)
        val gridlayout = GridLayoutManager(activity, 2)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvWard.adapter = storeAdapter
        rvWard.layoutManager = gridlayout
        storeAdapter!!.setOnItemClickListener(object : ListWardAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {
                moveScreen = MoveScreen(context)
                var district: String = listWard[position].district!!
                var bundle: Bundle = Bundle()
                bundle.putString("district", district)
                moveScreen!!.moveOneFragment(R.id.content, PlaceFragment(), bundle)
            }
        })

    }

    fun setHeader() {
        MainActivity.instance.imgFilter.visibility = View.VISIBLE
        MainActivity.instance.search_layout.visibility = View.VISIBLE
        MainActivity.instance.tv_title.visibility = View.GONE
        MainActivity.instance.chooseBottomView(2)
    }

    fun addList() {
        listWard.clear()
        service!!.getDistrict(MapFragment.City.mCity, "android").enqueue(object : Callback<List<District>> {
            override fun onFailure(call: Call<List<District>>?, t: Throwable?) {
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<District>>?, response: Response<List<District>>?) {
                if (response!!.isSuccessful) {
                    for (i in 0..(response.body()!!.size - 1)) {
                        listWard.add(response.body()!![i])
                    }
                    storeAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }



    override fun onPause() {
        super.onPause()
        MainActivity.instance.rlFilterCity.visibility = View.GONE
    }

}