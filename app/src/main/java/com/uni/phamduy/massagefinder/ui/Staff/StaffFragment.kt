package com.uni.phamduy.massagefinder.ui.Staff

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.uni.phamduy.coinmarket.networking.ApiUtils
import com.uni.phamduy.coinmarket.networking.SOService
import com.uni.phamduy.massagefinder.MainActivity
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListStaffAdapter
import com.uni.phamduy.massagefinder.module.staff.Staff
import com.uni.phamduy.massagefinder.ui.Place.PlaceDetailActivity
import com.uni.phamduy.massagefinder.utils.EndlessRecyclerViewScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by PhamDuy on 9/13/2017.
 */
class StaffFragment : Fragment() {
    private lateinit var rvStaff: RecyclerView
    private var staffAdapter: ListStaffAdapter? = null
    private var listStaff: MutableList<Staff> = ArrayList()
    private var service: SOService? = null
    lateinit var placeId: String
    lateinit var sortProperty: String
    lateinit var sortOrder: String
    lateinit var offset: String
    lateinit var limit: String
    lateinit var category: String
    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_staff, container, false)
        MainActivity.instance.chooseBottomView(3)
        service = ApiUtils.soService



        addParams()
        rvStaff = view!!.findViewById(R.id.rvStaff)
        init()

        return view
    }

    fun init() {
        listStaff.clear()
        addList("0")
        staffAdapter = ListStaffAdapter(activity, listStaff)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvStaff.adapter = staffAdapter
        rvStaff.layoutManager = linearLayoutManager
        staffAdapter!!.setOnItemClickListener(object : ListStaffAdapter.ClickListener {
            override fun OnItemReviewClick(position: Int, v: View) {
                val intent = Intent(context, ListReviewActivity::class.java)
                intent.putExtra("staffId", listStaff[position].id)
                intent.putExtra("placeId", listStaff[position].placeId)
                startActivityForResult(intent, 2)
            }

            override fun OnItemClick(position: Int, v: View) {

                val intent = Intent(context, PlaceDetailActivity::class.java)
                intent.putExtra("placeId", listStaff[position].placeId)
                startActivityForResult(intent, 2)
            }

        })
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                var count = (page * 10).toString()
                addList(count)
            }

        }
        rvStaff.addOnScrollListener(scrollListener)
    }

    private fun addParams() {
        var bundle = arguments
        placeId = if(bundle!=null) {
            bundle.getString("placeId").toString()
        }else{
            ""
        }
        sortProperty = "newestReviewDate"
        sortOrder = "ASC"
        offset = "0"
        limit = "10"
        category = "android"
    }

    private fun addList(mOffset:String) {
        service!!.getStaff(
                placeId,
                sortProperty,
                sortOrder,
                mOffset,
                limit,
                category
        ).enqueue(object : Callback<List<Staff>> {
            override fun onFailure(call: Call<List<Staff>>?, t: Throwable?) {
                Toast.makeText(activity, t.toString(),Toast.LENGTH_SHORT)
            }

            override fun onResponse(call: Call<List<Staff>>?, response: Response<List<Staff>>?) {
                if (response?.isSuccessful!!) {
                    for (i in 0 until response?.body()?.size!!) {
                        listStaff.add(response.body()!![i])
                    }
                    staffAdapter?.notifyDataSetChanged()
                }
            }

        })


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val result = data!!.getStringExtra("placeId")
            placeId = result
            listStaff.clear()
            addList("0")
            MainActivity.instance.chooseBottomView(3)
        }
    }

}