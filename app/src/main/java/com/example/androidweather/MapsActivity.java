package com.example.androidweather;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String lat = getIntent().getStringExtra(LATITUDE);
        String lng = getIntent().getStringExtra(LONGITUDE);
        Float latNum = Float.parseFloat(lat);
        Float lngNum = Float.parseFloat(lng);
        LatLng inputAddress = new LatLng(latNum, lngNum);
        mMap.addMarker(new MarkerOptions().position(inputAddress).title("Marker at Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inputAddress));

        String darkURL = "https://api.darksky.net/forecast/05ca222f08f58f2e81ebb62c497338a0/" + lat +"," + lng + "?exclude=minutely,hourly,daily,alerts,flags";
        HttpRequests http = new HttpRequests();
        http.changeURL(darkURL);
        http.execute();
        String response = null;
        try {
            response = http.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        String[] weatherInfo = new String[7];
        try {
            JSONObject darkResponse = new JSONObject(response.toString());
            weatherInfo[0] = darkResponse.getJSONObject("currently").getString("temperature");
            weatherInfo[1] = darkResponse.getJSONObject("currently").getString("humidity");
            weatherInfo[2] = darkResponse.getJSONObject("currently").getString("windSpeed");
            weatherInfo[3] = darkResponse.getJSONObject("currently").getString("precipIntensity");
            weatherInfo[4] = darkResponse.getJSONObject("currently").getString("precipIntensityError");
            weatherInfo[5] = darkResponse.getJSONObject("currently").getString("precipProbability");
            weatherInfo[6] = darkResponse.getJSONObject("currently").getString("precipType");

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
