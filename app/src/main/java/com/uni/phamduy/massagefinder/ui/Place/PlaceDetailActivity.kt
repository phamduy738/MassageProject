/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uni.phamduy.massagefinder.ui.Place

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.uni.phamduy.coinmarket.networking.ApiUtils
import com.uni.phamduy.coinmarket.networking.SOService
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListImageAdapter
import com.uni.phamduy.massagefinder.module.place.CoverImage
import com.uni.phamduy.massagefinder.module.place.Place
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.dialog_describe.*
import org.xml.sax.XMLReader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PlaceDetailActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {


    lateinit var listImageAdapter: ListImageAdapter
    var list: MutableList<String> = ArrayList()
    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    lateinit var mLocationRequest: LocationRequest
    private var mMap: GoogleMap? = null
    var mapFragment: SupportMapFragment? = null
    var titleDialog: String? = null
    var  textDialog: String? = null

    fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient!!.connect()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {

    }

    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        mMap!!.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    companion object {
        val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    lateinit var placeId:String
    lateinit var coverImage:CoverImage
    private var service: SOService? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        service = ApiUtils.soService
        loadBackdrop()
//        loadMap()

        placeId = intent.getStringExtra("placeId")
        initData()
        imgCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:0123456789")
            startActivity(intent)
        }
        rlListStaff.setOnClickListener(this)
        tvDescribeDetail.setOnClickListener(this)

        list.clear()
//        addList()
        listImageAdapter = ListImageAdapter(this, list)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.simple_drawable)
        rvListImage.addItemDecoration(DividerItemDecoration(dividerDrawable))
        rvListImage.adapter = listImageAdapter
        rvListImage.layoutManager = linearLayoutManager


    }
    fun initData(){
        service!!.getPlaceDetail(placeId, "android").enqueue(object : Callback<Place> {
            override fun onFailure(call: Call<Place>?, t: Throwable?) {
               Log.e("fail", t.toString())
            }

            override fun onResponse(call: Call<Place>?, response: Response<Place>?) {
                if (response!!.isSuccessful){
//                    collapsing_toolbar.title =  response.body()?.name
                    tvTitlePlace.text = response.body()?.name
                    tvAddress.text = response.body()?.address?.street
                    if(response.body()?.tickets?.size!! >0){
                        tvPrice.text = response.body()?.tickets?.get(0)?.price?.toString()
                    }else{
                        tvPrice.text = "0"
                    }

                    if (response.body()?.hotLines?.size!!>0){
                        tvPhone.text = response.body()?.hotLines?.get(0)
                    }else{
                        tvPhone.text ="Đang cập nhật"
                    }
                    if(response.body()?.rating!=null) {
                        tvRating.text =response.body()?.rating.toString() + "/10"
                    }else{
                        tvRating.text = ""
                    }
                    tvDescription.text = Html.fromHtml( response.body()?.review, null, UlTagHandler())
                    Glide.with(this@PlaceDetailActivity)
                            .load(response.body()?.coverImage?.link)
                            .placeholder(R.drawable.placeholder)
                            .into(backdrop)
                    var lat = response.body()?.address!!.latitude!!.toDouble()
                    var long = response.body()?.address!!.longitude!!.toDouble()
                    loadMap(lat, long, response.body()?.name!!, response.body()?.address?.street!!)

                    for (i in 0 until response.body()?.slideImages!!.size) {
                        list.add(response.body()?.slideImages!![i].link!!)
                    }
                    listImageAdapter.notifyDataSetChanged()
                    titleDialog = response.body()?.name
                    textDialog = response.body()?.review
                }
            }

        })

    }
    private fun loadMap( lat:Double,  long:Double, name:String, address: String){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        if (mapFragment != null) {
            mapFragment!!.getMapAsync { googleMap ->
                if (googleMap != null) {

                    googleMap.uiSettings.setAllGesturesEnabled(true)


                    // create marker
                    val marker = MarkerOptions().position(
                            LatLng(lat, long)).title(name)
                            .snippet(address)

                    googleMap.addMarker(marker)

                    val cameraPosition = CameraPosition.Builder().target(LatLng(lat, long)).zoom(15.0f).build()
                    val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                    googleMap.moveCamera(cameraUpdate)

                }
            }
        }
    }
    private fun loadBackdrop() {
//        Glide.with(this).load(R.drawable.image_massge).centerCrop().into(backdrop)

    }



    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvDescribeDetail -> {
                val menuDialog = Dialog(this)

                menuDialog.window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                val window: Window = menuDialog.window
                window.setGravity(Gravity.TOP)
                menuDialog.setContentView(R.layout.dialog_describe)
//                menuDialog.actionBar.subtitle= titleDialog

                menuDialog.window.attributes.windowAnimations = R.style.DialogAnimation
                menuDialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                menuDialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                menuDialog.tvTitle.text = titleDialog
                menuDialog.setCancelable(true)

                menuDialog.tvClose.setOnClickListener {
                    menuDialog.dismiss()
                }
                menuDialog.tvDescribe.text = Html.fromHtml(textDialog, null, UlTagHandler())
                menuDialog.show()
            }
            R.id.rlListStaff -> {
                val intent = Intent()
                intent.putExtra("placeId", placeId)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }
    }
     class UlTagHandler : Html.TagHandler {
        override fun handleTag(b: Boolean, s: String, editable: Editable, xmlReader: XMLReader) {
            if (s == "ul" && !b) editable.append("\n")
            if (s == "li" && b) editable.append("\n\t•")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
