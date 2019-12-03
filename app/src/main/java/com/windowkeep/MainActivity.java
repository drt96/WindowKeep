package com.windowkeep;


import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Button quoteButton;
    private Button b_Calender;

    private GoogleMap mMap;
    private static final LatLng STC = new LatLng(43.814588, -111.784767);
    private static final LatLng Benson = new LatLng(43.815455, -111.783062);
    private static final LatLng Austin = new LatLng(43.815703, -111.784499);
    private Marker mSTC;
    private Marker mBenson;
    private Marker mAustin;

   private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* Initializing the data */
        location = new Location(STC.latitude, STC.longitude);
        /*
        Create the on click listener and create the create quote activity
        quoteButton = findViewById(R.id.quoteButton);
        b_Calender = findViewById(R.id.b_Calender);
        */

    }

    /* Activity for quote view */
    public void openQuoteView(View view) {
        /* Hardcoded location for testing purposes. We will want to change the hardcoded 43, 111 values to whatever the location of the pin is */
        Intent intent = new Intent(this, CreateQuote_View.class);
        Bundle extras = new Bundle();

        extras.putDouble("latitude", location.getLatitude());
        extras.putDouble("longitude", location.getLongitude());
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
                 * LatLng:Class will give us selected position latigude and
                 * longitude values
                 */
                Toast.makeText(getApplicationContext(), latLng.toString(),
                        Toast.LENGTH_LONG).show();
                location = new Location(latLng.latitude, latLng.longitude);
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

        /* Retrieve the data from the marker. */
        Integer clickCount = (Integer) marker.getTag();

        /* Check if a click count was set, then display the click count. */
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            "location is Latitude: " + marker.getPosition().latitude + " Longitude: " + marker.getPosition().longitude,
                    Toast.LENGTH_LONG).show();
        }

        /*
         Return false to indicate that we have not consumed the event and that we wish
         for the default behavior to occur (which is for the camera to move such that the
         marker is centered and for the marker's info window to open, if it has one).
        */
        return false;
    }

    /*
 Main function that sets the initial long and lat for the map as well as its view type
    private void setupMap() {
        if (mMapView != null) {
            Basemap.Type basemapType = Basemap.Type.IMAGERY_WITH_LABELS;
            double latitude = 43.8231;
            double longitude = -111.7924;
            int levelOfDetail = 11;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);
        }
    }
    // Override functions for the Arcgis map
    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            mMapView.dispose();
        }
        super.onDestroy();
    }

    // This function is what asks for the user permission to access location and then show where they are located at.
    // Not sure if we want to prompt for permission or just do it automatically. For now it asks for permission.
    private void setupLocationDisplay() {
        mLocationDisplay = mMapView.getLocationDisplay();
        mLocationDisplay.addDataSourceStatusChangedListener(dataSourceStatusChangedEvent -> {
            if (dataSourceStatusChangedEvent.isStarted() || dataSourceStatusChangedEvent.getError() == null) {
                return;
            }

            int requestPermissionsCode = 2;
            String[] requestPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (!(ContextCompat.checkSelfPermission(MainActivity.this, requestPermissions[0]) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(MainActivity.this, requestPermissions[1]) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(MainActivity.this, requestPermissions, requestPermissionsCode);
            } else {
                String message = String.format("Error in DataSourceStatusChangedListener: %s",
                        dataSourceStatusChangedEvent.getSource().getLocationDataSource().getError().getMessage());
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
        mLocationDisplay.startAsync();
    }

    // Override for if the user chooses to not give location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationDisplay.startAsync();
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    // Adds the graphic overlay so that the object is visible on the map.
    private void createGraphicsOverlay() {
        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);
    }

    // This function is what creates the points on the map. For now I am just storing the location data as local variables then putting it into
    // a location object. This is still a work in progress until I can figure out how to make the points using data from Firebase.
    private void createPointGraphics() {
        //Local location variables to use until we get Firebase working
        double latitude = 43.814538;
        double longitude = -111.784559;
        Location loc1 = new Location(latitude, longitude);
        Point point = new Point(longitude,latitude, SpatialReferences.getWgs84());
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic pointGraphic = new Graphic(point, pointSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
    }
    // Main function called on setup to allow for multiple graphic integration
    private void createGraphics() {
        createGraphicsOverlay();
        createPointGraphics();
    }
*/


}

