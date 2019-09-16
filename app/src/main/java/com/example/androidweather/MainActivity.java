package com.example.androidweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    final String GOOGLE_API_KEY = "AIzaSyATf8zsOa012-zvbF-G3cglYTCeqKWaFBs";
    final String DARK_SKY_KEY = "05ca222f08f58f2e81ebb62c497338a0";
//    final String googleURL = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyATf8zsOa012-zvbF-G3cglYTCeqKWaFBs"
    Button mSubmitButton;
    EditText mAdd1;
    EditText mAdd2;
    EditText mCity;
    EditText mState;
    EditText mZipCode;
    String formattedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubmitButton = findViewById(R.id.buttonSubmit);
        mAdd1 = findViewById(R.id.addOneField);
        mAdd2 = findViewById(R.id.addTwoField);
        mCity = findViewById(R.id.cityField);
        mState = findViewById(R.id.stateField);
        mZipCode = findViewById(R.id.zipCodeField);

        mSubmitButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String addLine1 = mAdd1.getText().toString();
                        String addLine2 = mAdd2.getText().toString();
                        String city = mCity.getText().toString();
                        String state = mState.getText().toString();
                        String zip = mZipCode.getText().toString();
                        formattedAddress = addLine1 + " " + addLine2 + " " + city + " " + state + " " + zip;
                        formattedAddress.replace(' ', '+');
                        String finalUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + formattedAddress + "&key=AIzaSyATf8zsOa012-zvbF-G3cglYTCeqKWaFBs";
                        HttpRequests http = new HttpRequests();
                        http.changeURL(finalUrl);
                        http.execute();
                        String response = null;
                        String lat = null;
                        String lng = null;
                        try {
                            response = http.get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONObject myResponse = new JSONObject(response);
                            JSONArray results = myResponse.getJSONArray("results");
                            for(int i=0;i<results.length();i++)
                            {
                                JSONObject location = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");;
                                lat = location.optString("lat");
                                lng = location.optString("lng");
                                System.out.println(lat);
                                System.out.println(lng);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (lat != null && lng != null) {
                            Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                            mapsIntent.putExtra(LATITUDE, lat);
                            mapsIntent.putExtra(LONGITUDE, lng);
                            startActivity(mapsIntent);
                        } else {
                            Toast myToast = Toast.makeText(MainActivity.this, "INVALID ADDRESS",
                                    Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    }
                });

//        String finalUrl =

//        HttpRequests http = new HttpRequests();
//        String googleURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
//        String address = null;
//        http.changeURL("googleURL");
//        http.execute();
//        String response = null;
//        String lat  = null;
//        String lng  = null;
//        try {
//            response = http.get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            JSONObject myResponse = new JSONObject(response);
//            JSONArray results = myResponse.getJSONArray("results");
//            for(int i=0;i<results.length();i++)
//            {
//                JSONObject location = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");;
//                lat  = location.optString("lat");
//                lng  = location.optString("lng");
//                System.out.println(lat);
//                System.out.println(lng);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
       }

//    public void convertAddress(String fullAdd){
//        String addLine1 = mAdd1.getText().toString();
//        String addLine2 = mAdd2.getText().toString();
//        String city = mCity.getText().toString();
//        String state = mState.getText().toString();
//        String zip = mZipCode.getText().toString();
//        fullAdd = addLine1 + " " + addLine2 + " " + city + " " + state + " " + zip;
//        fullAdd.replace(' ', '+');
//    }


}
