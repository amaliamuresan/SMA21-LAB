package com.upt.cti.googlemapsproject

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import com.google.maps.android.ui.IconGenerator
import com.upt.cti.googlemapsproject.databinding.ActivityMapsBinding
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var REQ_PERMISSION: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        if(checkPermissions()) {
            mMap.isMyLocationEnabled = true
        } else {
            askPermission()
        }

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val facultate = LatLng(45.75371413897129, 21.225225870247037)
        val opera = LatLng(45.75410369470112, 21.225846468397574)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(facultate))

        val iconGenerator = IconGenerator(this)
        iconGenerator.setColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
        iconGenerator.setTextAppearance(android.R.color.black)
        mMap.addMarker(MarkerOptions().position(facultate).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("Facultate"))))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7F))

        mMap.setOnMarkerClickListener {marker ->
            if (marker.position == facultate) {
                Toast.makeText(this, "I'm studying", Toast.LENGTH_SHORT).show()
            }
            true
        }

        SphericalUtil.computeDistanceBetween(facultate, opera)
        drawPolylineOnMap(listOf(facultate, opera), mMap)
    }

    private fun checkPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this,
            listOf(android.Manifest.permission.ACCESS_FINE_LOCATION).toTypedArray(),
            REQ_PERMISSION
        )
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermissions())
                    mMap.isMyLocationEnabled = true
            }
        }
    }

    fun drawPolylineOnMap(list : List<LatLng>, googleMap: GoogleMap) {
        var polylineOptions = PolylineOptions()
        polylineOptions.color(Color.GREEN)
        polylineOptions.width(8F)
        polylineOptions.addAll(list)
        googleMap.addPolyline(polylineOptions)
        val builder : LatLngBounds.Builder = LatLngBounds.builder()
        for(latLng in list) {
            builder.include(latLng)
        }
        builder.build()
    }
}