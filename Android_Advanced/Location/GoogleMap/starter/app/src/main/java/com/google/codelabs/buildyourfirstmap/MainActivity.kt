// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codelabs.buildyourfirstmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.codelabs.buildyourfirstmap.place.Place
import com.google.codelabs.buildyourfirstmap.place.PlaceRenderer
import com.google.codelabs.buildyourfirstmap.place.PlacesReader
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.addCircle
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import kotlinx.android.synthetic.*

class MainActivity : AppCompatActivity() {

    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }

    private var circle: Circle? = null

    private val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_directions_bike_black_24dp, color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        // Map KTX, incorporate with kotlin coroutine
        lifecycleScope.launchWhenCreated {
            val googleMap = mapFragment.awaitMap()
            googleMap.awaitMapLoad()
            googleMap.uiSettings.isZoomControlsEnabled = true

            val bounds = LatLngBounds.builder()
            places.forEach { bounds.include(it.latLng) }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))

            addClusteredMarkers(googleMap)

        }

        // With Map SDK
//        val point = CameraUpdateFactory.newLatLng(LatLng(37.773972, -122.431297))
//
//        mapFragment?.getMapAsync { googleMap ->
//            googleMap.uiSettings.isZoomControlsEnabled = true
//
//            googleMap.setOnMapLoadedCallback {
//                val bounds = LatLngBounds.builder()
//                places.forEach { bounds.include(it.latLng) }
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
//            }
//            googleMap.moveCamera(point)
//            googleMap.animateCamera(point)

//            addClusteredMarkers(googleMap)

//            googleMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
//            addMarkers(googleMap)

//        }
    }

    private fun addClusteredMarkers(googleMap: GoogleMap) {

        val clusterManager = ClusterManager<Place>(this, googleMap)
        clusterManager.renderer = PlaceRenderer(this, googleMap, clusterManager)

        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        clusterManager.addItems(places)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque.
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that reclustering
            // can be performed when the camera stops moving.
            clusterManager.onCameraIdle()
        }

        googleMap.setOnCameraMoveStartedListener {
            // When the camera starts moving, change the alpha value of the marker to translucent
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        // show polygon
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(googleMap, item)
            return@setOnClusterItemClickListener false
        }



    }

    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker {
                title(place.name)
                position(place.latLng)
                icon(bicycleIcon)
            }
//            val marker = googleMap.addMarker(
//                MarkerOptions().title(place.name).position(place.latLng).icon(bicycleIcon)
//            )
            marker?.tag = place
        }

    }

    private fun addCircle(googleMap: GoogleMap, item: Place){
        circle?.remove()
//
//        circle = googleMap.addCircle(
//            CircleOptions().center(item.latLng)
//                .radius(1000.0)
//                .fillColor(ContextCompat.getColor(this, R.color.colorPrimaryTranslucent))
//                .strokeColor(ContextCompat.getColor(this, R.color.colorPrimary))
//        )
        circle = googleMap.addCircle {
            center(item.latLng)
            radius(1000.0)
            fillColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryTranslucent))
            strokeColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
        }

    }


}
