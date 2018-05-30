package com.uni.phamduy.massagefinder.ui.Staff

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.*
import com.uni.phamduy.coinmarket.networking.ApiUtils
import com.uni.phamduy.coinmarket.networking.SOService
import com.uni.phamduy.massagefinder.MainActivity
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListReviewAdapter
import com.uni.phamduy.massagefinder.module.review.Review
import com.uni.phamduy.massagefinder.ui.Place.PlaceDetailActivity
import com.uni.phamduy.massagefinder.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_list_review.*
import kotlinx.android.synthetic.main.dialog_describe.*
import kotlinx.android.synthetic.main.dialog_describe.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by PhamDuy on 10/30/2017.
 */
class ListReviewActivity: AppCompatActivity() {
    private var rvListReview:RecyclerView?=null
    var listReviewAdapter:ListReviewAdapter? = null
    var listReview:MutableList<Review> = ArrayList()
    private var staffId:String? = null
    private var service: SOService? = null
    private var placeId: String? = null
    private var sortProperty: String? = null
    private var sortOrder: String? = null
    private var offset: String? = null
    private var limit: String? = null
    private var category: String? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_review)
//        var toolbar: Toolbar = findViewById(R.id.toolbarReview)
        setSupportActionBar(toolbarReview)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        service = ApiUtils.soService
//        val intent = intent
        staffId = intent.getStringExtra("staffId")
        placeId = intent.getStringExtra("placeId")

      /*  tvMovePlace.setOnClickListener {

            val intent = Intent(this, PlaceDetailActivity::class.java)
            intent.putExtra("placeId", placeId)
            startActivityForResult(intent, 2)

        }*/
        addParams()
        rvListReview = findViewById(R.id.rvListReview)
        listReview.clear()
        addList("0")
        listReviewAdapter = ListReviewAdapter(this, listReview)
//        val gridlayout = GridLayoutManager(activity, 2)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvListReview?.adapter = listReviewAdapter
        rvListReview?.layoutManager = linearLayoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                var count = (page * 10).toString()
                addList(count)
            }

        }
        rvListReview?.addOnScrollListener(scrollListener)
        listReviewAdapter?.setOnItemClickListener(object : ListReviewAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {
                val menuDialog = Dialog(this@ListReviewActivity)

                menuDialog.window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                val window: Window = menuDialog.window
                window.setGravity(Gravity.TOP)
                menuDialog.setContentView(R.layout.dialog_describe)

                menuDialog.window.attributes.windowAnimations = R.style.DialogAnimation
                menuDialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                menuDialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                menuDialog.tvTitle.text = listReview[position].reviewTitle
                menuDialog.setCancelable(true)

                menuDialog.tvClose.setOnClickListener {
                    menuDialog.dismiss()
                }
                menuDialog.tvDescribe.text = Html.fromHtml(listReview[position].reviewContent, null, PlaceDetailActivity.UlTagHandler())
                menuDialog.show()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, PlaceDetailActivity::class.java)
                intent.putExtra("placeId", placeId)
                startActivityForResult(intent, 2)
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }
    private fun addParams() {
        sortProperty = "reviewDate"
        sortOrder = "ASC"
        offset = "0"
        limit = "10"
        category = "android"
    }
    fun addList(mOffset:String) {

       service!!.getListReview(staffId!!, sortProperty!!, sortOrder!!, mOffset!!, limit!!, category!!)
               .enqueue(object : Callback<List<Review>> {
                   override fun onResponse(call: Call<List<Review>>?, response: Response<List<Review>>?) {
                       if (response!!.isSuccessful){
                           for (i in 0 until response.body()?.size!!){
                               listReview.add(response.body()!![i])
                           }
                           listReviewAdapter?.notifyDataSetChanged()
                       }
                   }

                   override fun onFailure(call: Call<List<Review>>?, t: Throwable?) {
                       Log.e("fail", t.toString())
                   }

               })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val result = data!!.getStringExtra("placeId")
            placeId = result
            intent.putExtra("placeId", placeId)
            setResult(Activity.RESULT_OK,intent)
            finish()
            MainActivity.instance.chooseBottomView(3)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


}