package com.uni.phamduy.massagefinder.ui.Place

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uni.phamduy.coinmarket.networking.ApiUtils
import com.uni.phamduy.coinmarket.networking.SOService
import com.uni.phamduy.massagefinder.MainActivity
import com.uni.phamduy.massagefinder.MoveScreen
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.PlaceAdapter
import com.uni.phamduy.massagefinder.module.place.Place
import com.uni.phamduy.massagefinder.ui.Map.MapFragment
import com.uni.phamduy.massagefinder.ui.Staff.StaffFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import com.uni.phamduy.massagefinder.utils.EndlessRecyclerViewScrollListener


/**
 * Created by PhamDuy on 9/13/2017.
 */
class PlaceFragment : Fragment() {

    private var city: String? = null
    private var district: String? = null
    private var tags: String? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var sortProperty: String? = null
    private var sortOrder: String? = null
    private var offset: String? = null
    private var limit: String? = null
    private var category: String? = null

    private var moveScreen: MoveScreen? = null
    var rvPlace: RecyclerView? = null
    private var storeAdapter: PlaceAdapter? = null
    var listPlace: MutableList<Place> = ArrayList()
    private var service: SOService? = null
    private var idDistrict: String? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var posSort: Int? = 1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_place, container, false)
        service = ApiUtils.soService
        instance = this
        addParams()
        rvPlace = view!!.findViewById(R.id.rvPlace)
        init()
        MainActivity.instance.imgFilter.setOnClickListener {
            val fm = fragmentManager
            val sortDialog = SortDialogFragment()
            sortDialog.retainInstance = true
            sortDialog.show(fm, "fragment_sort")
        }

        return view
    }

    companion object {
        lateinit var instance: PlaceFragment
            private set
    }

    fun chooseSoort(p: Int) {/**/
        rvPlace?.scrollToPosition(0)
        posSort = p
        listPlace.clear()
        addList("0")
    }

    fun addParams() {
        var bundleDistrict = arguments
        city = MapFragment.City.mCity
        district = bundleDistrict.getString("district")!!.toString()
        tags = ""
        latitude = "0"
        longitude = "0"
        sortProperty = "rating"
        sortOrder = "ASC"
        offset = "0"
        limit = "10"
        category = "android"

    }

    fun init() {
        listPlace.clear()
        addList("0")
        storeAdapter = PlaceAdapter(activity, listPlace)
//        val gridlayout = GridLayoutManager(activity, 2)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvPlace?.adapter = storeAdapter
        rvPlace?.layoutManager = linearLayoutManager
        storeAdapter!!.setOnItemClickListener(object : PlaceAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {
                val intent = Intent(activity, PlaceDetailActivity::class.java)
                var placeId: String = listPlace[position].id!!
                intent.putExtra("placeId", placeId)
                startActivityForResult(intent, 1)
            }

        })
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                var count = (page * 10).toString()
                addList(count)
            }

        }
        rvPlace?.addOnScrollListener(scrollListener)
    }





    fun addList(mOffset: String) {
//        listPlace.clear()
        sortProperty = MapFragment.Sort.mSort
        service!!.getPlace(city!!,
                district!!,
                tags!!, latitude!!,
                longitude!!,
                sortProperty!!,
                sortOrder!!,
                mOffset!!,
                limit!!,
                category!!).enqueue(object : Callback<List<Place>> {
            override fun onFailure(call: Call<List<Place>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<List<Place>>?, response: Response<List<Place>>?) {
                if (response!!.isSuccessful) {
                    for (i in 0..(response.body()!!.size - 1)) {
                        listPlace.add(response.body()!![i])
                    }
                    storeAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val result = data!!.getStringExtra("placeId")
            var bundle: Bundle = Bundle()
            bundle.putString("placeId", result)
            moveScreen = MoveScreen(activity)
            moveScreen!!.moveOneFragment(R.id.content, StaffFragment(), bundle)
            MainActivity.instance.chooseBottomView(3)
        }
    }

    override fun onPause() {
        super.onPause()
        MainActivity.instance.rlSort.visibility = View.GONE
    }
}