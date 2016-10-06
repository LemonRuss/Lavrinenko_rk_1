package com.example.leman.lavrinenko_rk_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import ru.mail.weather.lib.*;


public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver br;

    private final View.OnClickListener onCityClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, CityActiviy.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener onUpdateClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            WeatherUtils.getInstance().schedule(MainActivity.this,
                    new Intent(Actions.LOAD.toString()));
        }
    };

    private final View.OnClickListener onCancelUpdateClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            WeatherUtils.getInstance().unschedule(MainActivity.this,
                    new Intent(Actions.LOAD.toString()));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.cityButton).setOnClickListener(onCityClick);
        findViewById(R.id.startUpdateBUtton).setOnClickListener(onUpdateClick);
        findViewById(R.id.endUpdateButton).setOnClickListener(onCancelUpdateClick);

        City city = WeatherStorage.getInstance(MainActivity.this)
                .getCurrentCity();
        ((Button) findViewById(R.id.cityButton)).setText(city.toString());

        Weather weather = WeatherStorage.getInstance(MainActivity.this)
                .getLastSavedWeather(city);
        if (weather != null) {
            setWeatherOnTextView(weather);
        }

        WeatherIntentService.start(this);
    }

    private void setWeatherOnTextView(Weather weather) {
        TextView textView = ((TextView) findViewById(R.id.infoTextView));
        Integer temp = weather.getTemperature();
        String description = weather.getDescription();
        String fullInfo = String.valueOf(temp) + "C  " + description;
        textView.setText(fullInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent.getAction().equals(Actions.LOADED.toString())) {
                        City city = WeatherStorage.getInstance(MainActivity.this)
                                .getCurrentCity();
                        Weather weather = WeatherStorage.getInstance(MainActivity.this).getLastSavedWeather(city);
                        if (weather != null) {
                            setWeatherOnTextView(weather);
                        }
                    }
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(Actions.LOADED.toString());
        LocalBroadcastManager.getInstance(this).registerReceiver(br, intFilt);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(br);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WeatherIntentService.start(this);

    }
}
