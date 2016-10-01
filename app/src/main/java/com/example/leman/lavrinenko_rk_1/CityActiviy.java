package com.example.leman.lavrinenko_rk_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.WeatherStorage;


public class CityActiviy extends AppCompatActivity {

    private final View.OnClickListener Back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = Integer.parseInt((String) view.getTag()) - 1;
            City city = City.values()[tag];
            WeatherStorage.getInstance(CityActiviy.this).setCurrentCity(city);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_city);

        findViewById(R.id.button4).setOnClickListener(Back);
        findViewById(R.id.button5).setOnClickListener(Back);
        findViewById(R.id.button6).setOnClickListener(Back);
        findViewById(R.id.button7).setOnClickListener(Back);
        findViewById(R.id.button8).setOnClickListener(Back);

    }

}
