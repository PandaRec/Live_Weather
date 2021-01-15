package com.example.liveweather;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.liveweather.MainActivity.*;


public class DownloadWeatherTask extends AsyncTask<String,Void,String> {


    @Override
    protected String doInBackground(String... strings) {
        URL url=null;
        HttpURLConnection urlConnection=null;
        StringBuilder result = new StringBuilder();

        try{
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line !=null){
                result.append(line);
                line = bufferedReader.readLine();
            }
            return result.toString();

        }catch (Exception e){
            Log.i("my_exception",e.toString());
        }finally {
            if(urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


}

