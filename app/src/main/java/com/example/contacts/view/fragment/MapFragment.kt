package com.example.contacts.view.fragment


import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.contacts.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import java.io.IOException
import java.util.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mapView != null){
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkAdress.visibility = View.GONE

        val latLng = LatLng(41.716667, 44.783333)
        val markerOptions: MarkerOptions = MarkerOptions().position(latLng).title("Tbilisi")


        val zoomLevel = 12.0f

        googleMap.let {
            it.addMarker(markerOptions)
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
            it.setOnMapClickListener {

                Toast.makeText(context, "${it.latitude}, ${it.longitude}", Toast.LENGTH_LONG ).show()
                val geocoder = Geocoder(context, Locale.getDefault())
                var addresses: List<Address>? = null
                try {
                    addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val adress = addresses!![0].getAddressLine(0)
                adressText.text = adress
                checkAdress.visibility = View.VISIBLE
                confirm.setOnClickListener {
                    val action = MapFragmentDirections.actionMapFragmentToAddContactFragment()
                    action.addressName = adress
                    Navigation.findNavController(it).navigate(action)
                }
                Log.d("Adreess->>>", adress)

            }
        }
    }




}
