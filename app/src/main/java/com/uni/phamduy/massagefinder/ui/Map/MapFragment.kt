package com.uni.phamduy.massagefinder.ui.Map

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.uni.phamduy.coinmarket.networking.ApiUtils
import com.uni.phamduy.coinmarket.networking.SOService
import com.uni.phamduy.massagefinder.MainActivity
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.module.place.Place
import com.uni.phamduy.massagefinder.utils.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlin.concurrent.thread


/**
 * Created by PhamDuy on 9/12/2017.
 */
class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    lateinit var mLocationRequest: LocationRequest
    private var mMap: GoogleMap? = null
    var mapFragment: SupportMapFragment? = null
    private var service: SOService? = null
    private var isCurrentLocation: Boolean? = false
    var listMarker: MutableList<MarkerOptions> = ArrayList()
//    var loadingDialog: ProgressDialog? = null

    private val REQUEST_CHECK_SETTINGS_GPS = 0x1
    private var city: String? = null
    private var district: String? = null
    private var tags: String? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var sortProperty: String? = null
    private var sortOrder: String? = null
    private var offset: String? = null
    private var limit: String? = null
    private var category: String? = null
    private var markerHandler = Handler()
    private var updateTimeRunnable: Runnable = object : Runnable {
        override fun run() {
            if (isCurrentLocation == true) {
                addListMarker()

            }
            markerHandler.postDelayed(this, 5000)
        }
    }
    class City {

        companion object {
            var mCity = "ho-chi-minh"
        }

    }

    class Sort {

        companion object {
            var mSort = "rating"
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_map, container, false)
//         loadingDialog = MainActivity.instance.showProgressDialog(activity)
//        loadingDialog!!.show()
        setHeader()
        service = ApiUtils.soService

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }
//        addList()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        markerHandler.post(updateTimeRunnable)


//        loadingDialog!!.dismiss()
        return view
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient!!.connect()
    }

    override fun onStop() {
        super.onStop()
        mGoogleApiClient!!.disconnect()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }

/**/    override fun onConnected(p0: Bundle?) {
        getMyLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Showing Current Location Marker on Map
        val latLng = LatLng(location!!.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), true)
        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val locations = locationManager.getLastKnownLocation(provider)
        val providerList = locationManager.allProviders
        if (null != locations && null != providerList && providerList.size > 0) {
            longitude = locations.longitude
            latitude = locations.latitude

//            addParams(latitude.toString(), longitude.toString())
//            addList()
            val geocoder = Geocoder(activity,
                    Locale.getDefault())
            try {
                val listAddresses = geocoder.getFromLocation(latitude!!,
                        longitude!!, 1)
                if (null != listAddresses && listAddresses.size > 0) {
                    val state = listAddresses[0].adminArea
                    val country = listAddresses[0].countryName
                    val subLocality = listAddresses[0].subLocality
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)
        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
        longitude = location.longitude
        latitude = location.latitude
        isCurrentLocation = true
        //this code stops location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this)
        }
    }

    private fun getMyLocation() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient!!.isConnected) {
                val permissionLocation = ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                    val locationRequest = LocationRequest()
                    locationRequest.interval = 3000
                    locationRequest.fastestInterval = 3000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    val builder = LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                    builder.setAlwaysShow(true)
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, this)
                    val result = LocationServices.SettingsApi
                            .checkLocationSettings(mGoogleApiClient, builder.build())
                    result.setResultCallback { result ->
                        val status = result.status
                        when (status.statusCode) {
                            LocationSettingsStatusCodes.SUCCESS -> {
                                // All location settings are satisfied.
                                // You can initialize location requests here.
                                val permissionLocation = ContextCompat
                                        .checkSelfPermission(activity,
                                                Manifest.permission.ACCESS_FINE_LOCATION)
                                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                    mLastLocation = LocationServices.FusedLocationApi
                                            .getLastLocation(mGoogleApiClient)
                                }
                            }
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                                // Location settings are not satisfied.
                                // But could be fixed by showing the user a dialog.
                                try {
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    // Ask to turn on GPS automatically
                                    status.startResolutionForResult(activity,
                                            REQUEST_CHECK_SETTINGS_GPS)
                                } catch (e: IntentSender.SendIntentException) {
                                    // Ignore the error.
                                }

                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                AlertDialog.Builder(activity)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.title_location_permission)
                        .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        })
                        .create()
                        .show()

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
        } else {
            getMyLocation()
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
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getMyLocation()
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }

                        mMap!!.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(activity, "permission denied",
                            Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    companion object {
        val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    fun addParams() {
        city = City.mCity
        district = ""
        tags = ""
        sortProperty = "rating"
        sortOrder = "ASC"
        offset = "0"
        limit = "10"
        category = "android"

    }

    fun addListMarker() {
        addParams()
        service?.getPlace(city!!,
                district!!,
                tags!!, latitude.toString()!!,
                longitude.toString()!!,
                sortProperty!!,
                sortOrder!!,
                offset!!,
                limit!!,
                category!!)?.enqueue(object : Callback<List<Place>> {
            override fun onFailure(call: Call<List<Place>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Place>>?, response: Response<List<Place>>?) {
                if (response!!.isSuccessful) {
                    for (i in 0..(response.body()!!.size - 1)) {
                        var lat = response.body()!![i].address?.latitude!!.toDouble()
                        var long = response.body()!![i].address?.longitude!!.toDouble()
                        var title = response.body()!![i].name
                        var snippet = response.body()!![i].address?.street
                        var marker = MarkerOptions().position(
                                LatLng(lat, long)).title(title)
                                .snippet(snippet)
                        listMarker.add(marker)
                        mCurrLocationMarker = mMap!!.addMarker(marker)
                    }
                    markerHandler.removeCallbacks(updateTimeRunnable)
                }
//                loadingDialog!!.dismiss()
            }

        })
    }




    fun setHeader() {
        MainActivity.instance.imgFilter.visibility = View.GONE
        MainActivity.instance.search_layout.visibility = View.GONE
        MainActivity.instance.tv_title.visibility = View.VISIBLE
        MainActivity.instance.chooseBottomView(1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS_GPS -> when (resultCode) {
                Activity.RESULT_OK -> getMyLocation()
                Activity.RESULT_CANCELED -> activity.finish()
            }
        }
    }
}