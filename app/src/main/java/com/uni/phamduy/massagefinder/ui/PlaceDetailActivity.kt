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

package com.uni.phamduy.massagefinder.ui

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.Html
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
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListImageAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.dialog_describe.*
import org.xml.sax.XMLReader
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

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = "Massage Trường Đua"
        loadBackdrop()
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
                            LatLng(10.7975349, 106.6468148)).title("tphcm")
                            .snippet("here is my location")

                    googleMap.addMarker(marker)

                    val cameraPosition = CameraPosition.Builder().target(LatLng(10.7975349, 106.6468148)).zoom(15.0f).build()
                    val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                    googleMap.moveCamera(cameraUpdate)

                }
            }
        }

        tv_list_employee.setOnClickListener(this)
        tvDescribeDetail.setOnClickListener(this)

        list.clear()
        addList()
        listImageAdapter = ListImageAdapter(this, list)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.simple_drawable)
        rvListImage.addItemDecoration(DividerItemDecoration(dividerDrawable))
        rvListImage.adapter = listImageAdapter
        rvListImage.layoutManager = linearLayoutManager


    }

    private fun loadBackdrop() {
        Glide.with(this).load(R.drawable.image_massge).centerCrop().into(backdrop)

    }

    fun addList() {
        for (i in 0..10) {
            list.add(i.toString())
        }
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

                menuDialog.window.attributes.windowAnimations = R.style.DialogAnimation
                menuDialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                menuDialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                menuDialog.setCancelable(true)

                menuDialog.tvClose.setOnClickListener {
                    menuDialog.dismiss()
                }
                menuDialog.tvDescribe.text = Html.fromHtml("<ul>\r\n\t<li style=\"text-align:justify\">Tọa lạc tại một con hẻm 606/71 Đường 3 Th&aacute;ng 2, Phường 14, Quận 10, <strong>Hana Chicken</strong> l&agrave; điểm hẹn ăn uống l&yacute; tưởng d&agrave;nh cho mọi người, đến đ&acirc;y bạn được kh&aacute;m ph&aacute;, thưởng thức những m&oacute;n ăn được chế biến c&ocirc;ng phu, cầu kỳ, vị thơm ngon, tinh tế.</li>\r\n\t<li style=\"text-align:justify\"><strong>Hana Chicken</strong> sở hữu thực đơn ăn uống đa dạng, phong ph&uacute;, đ&aacute;p ứng cho nhu cầu, sở th&iacute;ch của từng người: G&agrave; ph&ocirc; mai, tokpokki, soup kim chi v&agrave; nhiều m&oacute;n ăn k&egrave;m: Cơm rong biển, khoai đ&uacute;t l&ograve;, khoai t&acirc;y chi&ecirc;n, salad&hellip;</li>\r\n\t<li style=\"text-align:justify\">G&agrave; r&aacute;n 4 loại sốt l&agrave; một trong những m&oacute;n ăn hiện &ldquo;l&agrave;m mưa l&agrave;m gi&oacute;&rdquo; tại qu&aacute;n, được nhiều người ưa th&iacute;ch. G&agrave; được tẩm gia vị đầy đủ rồi chi&ecirc;n với lớp bột cũng được tẩm vị đậm đ&agrave;, g&agrave; r&aacute;n với độ vừa phải, vỏ gi&ograve;n, thịt kh&ocirc;ng bở m&agrave; mềm ăn k&egrave;m với 4 loại sốt như ti&ecirc;u đen, ph&ocirc; mai, bơ tỏi, mật ong, chanh d&acirc;y.</li>\r\n\t<li style=\"text-align:justify\">Tất cả những m&oacute;n ăn tại <strong>Hana Chicken</strong> đều được l&agrave;m từ nguy&ecirc;n liệu sạch, đảm bảo vệ sinh an to&agrave;n thực phẩm v&agrave; được đầu bếp tay nghề cao, d&agrave;y dạn kinh nghiệm chế biến.</li>\r\n\t<li style=\"text-align:justify\"><strong>Hana Chicken</strong> với kh&ocirc;ng gian kh&ocirc;ng cầu kỳ, sang trọng nhưng mọi thứ đều được thiết kế, b&agrave;y tr&iacute; một c&aacute;ch h&agrave;i h&ograve;a v&agrave; đội ngũ nh&acirc;n vi&ecirc;n phục vụ chuy&ecirc;n nghiệp, chu đ&aacute;o, th&acirc;n thiện, sẽ mang đến cho bạn những ph&uacute;t gi&acirc;y ăn uống thỏa m&atilde;n nhất.</li>\r\n</ul>", null,  UlTagHandler())
                menuDialog.show()
            }
            R.id.tv_list_employee -> {
                val intent = Intent()
                intent.putExtra("result", "www")
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
