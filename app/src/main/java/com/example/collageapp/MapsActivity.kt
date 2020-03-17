package com.example.collageapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.collageapp.common.Common
import com.example.collageapp.model.MyPlace
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latitude:Double=0.toDouble()
    private var longitude:Double=0.toDouble()

    private lateinit var  mLastLocation : Location
    var mMarker: Marker?=null


    private val mViewModel by lazy {
        ViewModelProvider(this).get(MapsViewModel::class.java)
    }

    private val markerOptions= MarkerOptions()
    //Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    companion object{
        private const val MY_PERMISSION_CODE = 1000
    }


    private lateinit var currentPlace: MyPlace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission()
        }
        buildLocationRequest()
        buildLocationCellBack()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.myLooper())

        mainBottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_hospital -> getNearByPlace("hospital")
                R.id.action_market -> getNearByPlace("market")
                R.id.action_restaurant -> getNearByPlace("restaurant")
                R.id.action_school -> getNearByPlace("school")
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun getNearByPlace(typePlace: String) {
        mViewModel.getNearByPlaces(latitude, longitude, typePlace)
        showNearByPlaces(typePlace)
    }

    private fun showNearByPlaces(typePlace: String) {
        mMap.clear()
        mViewModel.currentPlace.observe(this, Observer { myPlace ->
            currentPlace=myPlace
            for (i in myPlace.results!!.indices ){
                val googlePlace = myPlace.results!![i]
                val lat = googlePlace.geometry!!.location!!.lat
                val lng = googlePlace.geometry!!.location!!.lng
                val latLng = LatLng(lat,lng)
                val placeName = googlePlace.name
                markerOptions.position(latLng)
                markerOptions.title(placeName)
                mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(20f))
                showMarkOnMap(typePlace)
            }

        })
    }
    private fun showMarkOnMap(typePlace: String){
        when (typePlace) {
            "hospital" -> {
                markerOptions.icon(bitmapDescriptorFromVector(this@MapsActivity,R.drawable.ic_hospital_black_24dp))
                Log.d("check",typePlace)

            }
            "market" -> {
                markerOptions.icon(bitmapDescriptorFromVector(this@MapsActivity,R.drawable.ic_shop_black_24dp))
                Log.d("check",typePlace)
            }
            "restaurant" -> {
                markerOptions.icon(bitmapDescriptorFromVector(this@MapsActivity,R.drawable.ic_restaurant_black_24dp))
                Log.d("check",typePlace)
            }
            "school" -> {
                markerOptions.icon(bitmapDescriptorFromVector(this@MapsActivity,R.drawable.ic_school_black_24dp))
                Log.d("check",typePlace)
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled=true
            }
        }
        else
            mMap.isMyLocationEnabled=true

        mMap.uiSettings.isZoomControlsEnabled=true

        mMap.setOnMarkerClickListener {

            Log.d("CurrentResult",""+it.position.latitude)
            for(i in currentPlace.results!!.indices){
                val googlePlace = currentPlace.results!![i]
                val lat = googlePlace.geometry!!.location!!.lat
                val lng = googlePlace.geometry!!.location!!.lng
                val latLng = LatLng(lat,lng)
                if(it.position==latLng){
                    Common.currentResult = currentPlace.results!![i]
                    Log.d("CurrentResult2",""+Common.currentResult!!.name)
                }
            }
            if(Common.currentResult!=null){
                val i = Intent(this,ViewPlaceActivity::class.java)
                startActivity(i)

            }
            true
        }
    }


    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun buildLocationCellBack() {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.locations[p0.locations.size-1]
                if(mMarker != null){
                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                Log.d("lat" ,latitude.toString())

                Log.d("lon" ,longitude.toString())
                val latLng =LatLng(latitude,longitude)
                val markerOptions =MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title("Your Location")
                mMarker = mMap.addMarker(markerOptions)

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.zoomBy(11f))
            }
        }
    }

    private fun checkLocationPermission() : Boolean {
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                ,MY_PERMISSION_CODE)
            else
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    ,MY_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MY_PERMISSION_CODE -> {
                if( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()){
                            mMap.isMyLocationEnabled=true
                        }
                }
                else
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }
}
