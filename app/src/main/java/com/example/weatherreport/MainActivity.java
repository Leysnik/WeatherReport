package com.example.weatherreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText cityName;
    Button btn;
    TextView description;
    TextView temp;
    TextView wind;
    Handler handler;

    public static final String API ="85df75603fcd567eeb1f57725e72dc6f";
    public static final String URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.city_name);
        btn = findViewById(R.id.get_btn);
        description = findViewById(R.id.description);
        temp = findViewById(R.id.temp);
        wind = findViewById(R.id.wind);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String res = msg.getData().getString("body");
                //description.setText(res);

//                try {
//                    JSONObject result = new JSONObject(res);
//                    JSONObject main = result.getJSONObject("main");
//                    temp.setText( "Температура: " + main.getString("temp") +
//                            "\nМаксимальная температура: " + main.getString("temp_max")+
//                            "\nМинимальная температура: " + main.getString("temp_min"));
//                    JSONArray array = result.getJSONArray("weather");
//                    JSONObject weather = array.getJSONObject(0);
//                    description.setText("Сегодня на улице " + weather.getString("description"));
//                    JSONObject windJ = result.getJSONObject("wind");
//                    wind.setText("Скорость ветра: " + windJ.getString("speed"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                Gson gson = new Gson();
                WeatherInform weatherInform = gson.fromJson(res,WeatherInform.class);
                description.setText("Сегодня на улице " + weatherInform.weather[0].description);
                temp.setText("Температура:  " + weatherInform.main.temp +"\nМаксимальная температура: " + weatherInform.main.temp_max+ "\nМинимальная температура: " + weatherInform.main.temp_max);
                wind.setText("Скорость ветра:  " + weatherInform.wind.speed);

            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherThread thread = new WeatherThread(handler,cityName.getText().toString());
                thread.start();
            }
        });
    }
}