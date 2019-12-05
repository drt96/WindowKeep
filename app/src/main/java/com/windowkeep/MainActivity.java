package com.windowkeep;


import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.os.Bundle;
import android.location.Location;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    private static final LatLng STC = new LatLng(43.814588, -111.784767);
    private static final LatLng Benson = new LatLng(43.815455, -111.783062);
    private static final LatLng Austin = new LatLng(43.815703, -111.784499);
    private Marker mSTC;
    private Marker mBenson;
    private Marker mAustin;

    private ID id;
    android.location.Location aLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* Initializing the data */
        id = new ID(STC.latitude, STC.longitude);

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

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        /* Add some markers to the map, and add a data object to each marker. */
        mSTC = mMap.addMarker(new MarkerOptions()
                .position(STC)
                .title("STC"));
        mSTC.setTag(0);

        mBenson = mMap.addMarker(new MarkerOptions()
                .position(Benson)
                .title("Benson"));
        mBenson.setTag(0);

        mAustin = mMap.addMarker(new MarkerOptions()
                .position(Austin)
                .title("Austin"));
        mAustin.setTag(0);

        /* Set a listener for marker click. */
        mMap.setOnMarkerClickListener(this);

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
}

