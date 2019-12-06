package com.windowkeep;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ID id;
    private Marker marker;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 14f;


//    private static final LatLng STC = new LatLng(43.814588, -111.784767);
//    private static final LatLng Benson = new LatLng(43.815455, -111.783062);
//    private static final LatLng Austin = new LatLng(43.815703, -111.784499);
//    private Marker mSTC;
//    private Marker mBenson;
//    private Marker mAustin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocationPermission();



        /* Initializing the data */
//        id = new ID(STC.latitude, STC.longitude);

    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Zoom buttons- causes app to crash ?
        //mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }

//        /* Add some markers to the map, and add a data object to each marker. */
//        mSTC = mMap.addMarker(new MarkerOptions()
//                .position(STC)
//                .title("STC"));
//        mSTC.setTag(0);
//
//        mBenson = mMap.addMarker(new MarkerOptions()
//                .position(Benson)
//                .title("Benson"));
//        mBenson.setTag(0);
//
//        mAustin = mMap.addMarker(new MarkerOptions()
//                .position(Austin)
//                .title("Austin"));
//        mAustin.setTag(0);


        /* Set a listener for marker click. */
        mMap.setOnMarkerClickListener(this);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.remove();
            }
        });

        /* To listen action whenever we click on the map */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                /*
                 * LatLng:Class will give us selected position latitude and
                 * longitude values
                 */

                Toast.makeText(getApplicationContext(), latLng.toString(),
                        Toast.LENGTH_LONG).show();
                id = new ID(latLng.latitude, latLng.longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title("Location: " + latLng.latitude + ":" + latLng.longitude);
                markerOptions.position(latLng);
                mMap.addMarker(markerOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }

    /* Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

            Toast.makeText(this,
                    marker.getTitle() +
                            "location is Latitude: " + marker.getPosition().latitude + " Longitude: " + marker.getPosition().longitude,
                    Toast.LENGTH_LONG).show();

        return false;
    }

    /* Activity for quote view */
    public void openQuoteView(View view) {
        /* Hardcoded location for testing purposes. We will want to change the hardcoded 43, 111 values to whatever the location of the pin is */
        Intent intent = new Intent(this, CreateQuote_View.class);
        Bundle extras = new Bundle();

        extras.putDouble("latitude", id.getLatitude());
        extras.putDouble("longitude", id.getLongitude());
        intent.putExtras(extras);

        CreateQuote_View.resetQuoteFields();
        startActivity(intent);
    }

    /* Activity for calender view */
    public void openCalenderView(View view) {
        Intent intent = new Intent(this, AppointmentList_View.class);
        startActivity(intent);
    }


}

