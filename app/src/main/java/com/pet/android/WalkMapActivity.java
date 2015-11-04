package com.pet.android;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WalkMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location mLoc;
    private Marker mMarker; //may be a list of markers
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            initLocationManager();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("mohl", "== onLocationChanged: lat = " + location.getLatitude() + ", lon = " + location.getLongitude());
        mLoc = location;
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        moveMap(mMap, loc);
    }

    private void initLocationManager() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //define the location manager criteria
        Criteria criteria = new Criteria();
        String locationProvider = mLocationManager.getBestProvider(criteria, false);
        Location location = mLocationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            onLocationChanged(location);
        }
    }

    private void moveMap(GoogleMap map, LatLng place) {
        if (map != null) {
            if(mMarker != null)  mMarker.remove();
            mMarker = mMap.addMarker(new MarkerOptions().position(place).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.paw)));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(place)
                    .zoom(17)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
