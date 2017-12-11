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
import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.module.place.Place
import com.uni.phamduy.massagefinder.ui.PlaceDetailActivity
import com.uni.phamduy.massagefinder.ui.Staff.StaffFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*



/**
 * Created by PhamDuy on 9/13/2017.
 */
class PlaceFragment : Fragment() {



    private var moveScreen: MoveScreen? = null
    private lateinit var rvPlace: RecyclerView
    private var storeAdapter: PlaceAdapter? = null
    private var listPlace: MutableList<Place> = ArrayList()
    private var service: SOService? = null
    private var idDistrict:String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_place, container, false)
        service = ApiUtils.soService
        rvPlace = view!!.findViewById(R.id.rvPlace)
        init()

        return view
    }

    fun init() {
        listPlace.clear()
        addList()
        storeAdapter = PlaceAdapter(activity, listPlace)
//        val gridlayout = GridLayoutManager(activity, 2)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvPlace.adapter = storeAdapter
        rvPlace.layoutManager = linearLayoutManager
        storeAdapter!!.setOnItemClickListener(object : PlaceAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {
                val intent = Intent(activity, PlaceDetailActivity::class.java)
                intent.putExtra("abc", "abc")
               startActivityForResult(intent,1)
            }

        })
    }



    fun addList() {
       service!!.getPlace("ho-chi-minh",
               "quan-5",
               "","10.7565608",
               "106.6702963",
               "rating",
               "ASC",
               "0",
               "10",
               "android").enqueue(object :Callback<List<Place>>{
           override fun onFailure(call: Call<List<Place>>?, t: Throwable?) {

           }

           override fun onResponse(call: Call<List<Place>>?, response: Response<List<Place>>?) {
               if(response!!.isSuccessful){
                   for(i in 0..(response.body()!!.size-1)) {
                       listPlace.add(response.body()!![i])
                   }
                   storeAdapter!!.notifyDataSetChanged()
               }
           }

       })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1&& resultCode== Activity.RESULT_OK){
            val result = data!!.getStringExtra("result")
            moveScreen = MoveScreen(activity)
            moveScreen!!.clickedOn(R.id.content, StaffFragment())
            MainActivity.instance.chooseBottomView(3)
        }
    }


}