package com.example.liveweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public EditText editTextCity;
    public Button buttonSearch;
    public TextView textViewCity;
    public TextView textViewTime;
    public TextView textViewTemperature;
    public TextView textViewLastUpdate;
    public ImageView imageViewTypeOfWeather;

    private final String WEATHER_URL= "http://api.weatherapi.com/v1/current.json?key=fbe591062b3f49a5a0f164435202212&q=%s";
    private final String CONDITIONS = "https://www.weatherapi.com/docs/weather_conditions.json";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCity = findViewById(R.id.editTextTextLocalization);
        buttonSearch = findViewById(R.id.button);
        textViewCity = findViewById(R.id.textViewCity);
        textViewTime = findViewById(R.id.textViewCurrentTime);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewLastUpdate = findViewById(R.id.textViewLastUpdate);
        imageViewTypeOfWeather = findViewById(R.id.imageView);





    }

    private static ArrayList<String> getWeatherFromJson(String JSON){
        ArrayList<String> result = new ArrayList<>();
        String city;
        String time;
        String temperature;
        String lastUpdate;
        String pictureCode;
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            JSONObject location = jsonObject.getJSONObject("location");
            JSONObject current = jsonObject.getJSONObject("current");
            JSONObject condition = current.getJSONObject("condition");
            city = location.getString("name");
            time = location.getString("localtime");
            lastUpdate = current.getString("last_updated");
            pictureCode = condition.getString("code");
            temperature = current.getString("temp_c");
            result.add(city);
            result.add(time);
            result.add(temperature);
            result.add(lastUpdate);
            result.add(pictureCode);
            return result;

        }catch (Exception e){

        }
        return null;
    }



    private static void setImage(String code){

    }



    public void onClickButtonSearchPressed(View view) {

        String localization = editTextCity.getText().toString().trim();
        String url = String.format(WEATHER_URL,localization);
        String JSON=null;
        ArrayList<String> weather = new ArrayList<>();


        DownloadWeatherTask downloadWeatherTask = new DownloadWeatherTask();
        try{
            JSON = downloadWeatherTask.execute(url).get();
        }catch (Exception e){

        }
        if(JSON==null){
            return;
        }
        weather = getWeatherFromJson(JSON);


        textViewCity.setText("city - "+weather.get(0));
        textViewTime.setText("time - "+weather.get(1));
        textViewTemperature.setText("temp - "+weather.get(2));
        textViewLastUpdate.setText("last - "+weather.get(3));

    }
}