package de.joeybag.joeybag.ui.partners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import de.joeybag.joeybag.R
import kotlinx.android.synthetic.main.fragment_partners.*

class PartnersFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map.onCreate(savedInstanceState)
        map.onResume()

        map.getMapAsync(this)
    }

    override fun onMapReady(MAP: GoogleMap?) {
        MAP?.let {
            googleMap = it
            val zoomLevel = 12.0f //This goes up to 21

            // Markers of partners and headquarters including location of operation
            val partner1 = LatLng(49.0110, 8.4236)
            MAP.addMarker(MarkerOptions().position(partner1).title("unser Partner - Tante M"))
            val partner2 = LatLng(49.0356, 8.4446)
            MAP.addMarker(MarkerOptions().position(partner2).title("unser Partner - EDEKA Behrens"))
            val partner3 = LatLng(49.0453, 8.3829)
            MAP.addMarker(MarkerOptions().position(partner3).title("unser Partner - EDEKA Rees"))
            val partner4 = LatLng(48.9947, 8.3997)
            MAP.addMarker(MarkerOptions().position(partner4).title("unser Partner - unverpackt"))
            val headquarters = LatLng(49.0066, 8.4320)
            MAP.addMarker(MarkerOptions().position(headquarters).title("unsere Zentrale - Karlsruhe"))
            val location = LatLng(49.0069, 8.4037)
            MAP.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
            MAP.moveCamera(CameraUpdateFactory.newLatLng(headquarters))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_partners, container, false)
    }
}




// Code for OpenStreetMap

//import android.graphics.BitmapFactory
//import android.os.Bundle
//import android.preference.PreferenceManager
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
////import com.app.base.BuildConfig
////import de.joeybag.joeybag.base_classes.BaseFragment
////import com.google.android.gms.maps.GoogleMap
//import de.joeybag.joeybag.BuildConfig
//import org.osmdroid.api.IMapController
//import org.osmdroid.config.Configuration
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory
//import org.osmdroid.views.MapView
//import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
//import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
//
//class PartnersFragment : Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        val view = inflater.inflate(de.joeybag.joeybag.R.layout.fragment_partners, container, false)
//
//        val ctx = requireActivity().applicationContext
//        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
//        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
//        val map = view.findViewById<MapView>(de.joeybag.joeybag.R.id.mapview)
//
//        map.setUseDataConnection(true)
//        //val map = view.findViewById(R.id.map) as MapView
//        map.setTileSource(TileSourceFactory.MAPNIK)
//        //map.setBuiltInZoomControls(true) //Map ZoomIn/ZoomOut Button Visibility
//        map.setMultiTouchControls(true)
//        val mapController: IMapController
//        mapController = map.getController()
//
//        //mapController.zoomTo(14, 1)
//        mapController.setZoom(14)
//
//        val mGpsMyLocationProvider = GpsMyLocationProvider(activity)
//        val mLocationOverlay = MyLocationNewOverlay(mGpsMyLocationProvider, map)
//        mLocationOverlay.enableMyLocation()
//        mLocationOverlay.enableFollowLocation()
//
//        val icon = BitmapFactory.decodeResource(resources, de.joeybag.joeybag.R.drawable.icon_current_location)
//        mLocationOverlay.setPersonIcon(icon)
//        map.getOverlays().add(mLocationOverlay)
//
//        mLocationOverlay.runOnFirstFix {
//            map.getOverlays().clear()
//            map.getOverlays().add(mLocationOverlay)
//            mapController.animateTo(mLocationOverlay.myLocation)
//        }
//        return view
//    }
//}

