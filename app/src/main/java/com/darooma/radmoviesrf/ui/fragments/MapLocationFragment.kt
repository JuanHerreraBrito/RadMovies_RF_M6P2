package com.darooma.radmoviesrf.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darooma.radmoviesrf.R
import com.darooma.radmoviesrf.databinding.FragmentMapLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapLocationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapLocationBinding? = null
    private val binding get() = _binding!!
    //Para google maps
    private lateinit var map: GoogleMap
    private var lat = ""
    private var long = ""
    private var desc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            lat = it.getString("ARG_LAT").toString()
            long = it.getString("ARG_LONG").toString()
            desc = it.getString("ARG_DESC").toString()
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapLocationBinding.inflate(inflater, container, false)

        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(lat: String?, long: String?, desc: String?) =
            MapLocationFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_LAT", lat)
                    putString("ARG_LONG", long)
                    putString("ARG_DESC", desc)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker(){
        //val coordinates = LatLng(19.322326, -99.184592)
        val coordinates = LatLng(lat.toDouble(), long.toDouble())
        val marker = MarkerOptions()
            .position(coordinates)
            .title(getString(R.string.title_location))
            .snippet(desc)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.nav_icons))//cambiar por uno de pelicula

        map.addMarker(marker)

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            3000,
            null
        )
    }


}