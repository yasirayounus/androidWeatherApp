package com.example.androidweather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Andrea driving
public class HttpRequests extends AsyncTask<String, Void, String> {
    final String API_KEY = "AIzaSyATf8zsOa012-zvbF-G3cglYTCeqKWaFBs";
    String url;

    public void changeURL(String urlAddress){
        url = urlAddress;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(this.url);
            //Setting up connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //Reading error codes
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Test to see if HTTP response is working
        System.out.println(response.toString());
        System.out.println("PRINTED");
        return response.toString();
    }

}
