package com.example.androidweatherapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new HttpRequests().execute("hello");

    }

    private class HttpRequests extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String address = "";
            for (String i: strings){
                address = i;
            }
            String latlng = getLatLng("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=");
            return null;
        }
        private String[] getWeatherInfo(String latlng){
            String API_KEY = "9ed8e0b221fae0211ec79d1ccb01c974";
            StringBuffer response = new StringBuffer();
            try {
                //System.out.println("Hello");
                URL url = new URL("https://api.darksky.net/forecast/" + API_KEY + "/" +latlng + "?exclude=minutely,hourly,daily,alerts,flags");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setReadTimeout(15000);
                con.setConnectTimeout(15000);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");

                int responseCode = con.getResponseCode();
                //System.out.println("RC:" + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } catch (ProtocolException e) {

            } catch (MalformedURLException e) {

            } catch (IOException e) {

            }
            System.out.println(response.toString());
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
            return weatherInfo;
        }
        private String getLatLng(String urlString) {
            String API_KEY = "AIzaSyATf8zsOa012-zvbF-G3cglYTCeqKWaFBs";
            StringBuffer response = new StringBuffer();
            try {
                URL url = new URL(urlString + API_KEY);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setReadTimeout(15000);
                con.setConnectTimeout(15000);
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } catch (ProtocolException e) {

            } catch (MalformedURLException e) {

            } catch (IOException e) {

            }
            String lat = "";
            String lng = "";
            try {
                JSONObject myResponse = new JSONObject(response.toString());
                JSONArray results = myResponse.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject location = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                    lat = location.optString("lat");
                    lng = location.optString("lng");
                    //System.out.println("LATiTUDE" + lat+","+lng);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return lat + "," + lng;
        }
    }
}
