package com.example.leman.lavrinenko_rk_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.weather.lib.*;


public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br;

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


        findViewById(R.id.button).setOnClickListener(onCityClick);
        findViewById(R.id.button2).setOnClickListener(onUpdateClick);
        findViewById(R.id.button3).setOnClickListener(onCancelUpdateClick);

        City city = WeatherStorage.getInstance(MainActivity.this)
                .getCurrentCity();
        ((Button) findViewById(R.id.button)).setText(city.toString());

        Weather weather = WeatherStorage.getInstance(MainActivity.this)
                .getLastSavedWeather(city);
        if (weather != null) {
            TextView textView = ((TextView) findViewById(R.id.textView));
            Integer temp = weather.getTemperature();
            textView.setText(String.valueOf(temp));
        }

        WeatherIntentService.startActionFoo(this);

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent.getAction() == Actions.LOADED.toString()) {
                        City city = WeatherStorage.getInstance(MainActivity.this)
                                .getCurrentCity();
                        Weather weather = WeatherStorage.getInstance(MainActivity.this).getLastSavedWeather(city);
                        TextView textView = ((TextView) findViewById(R.id.textView));
                        Integer temp = weather.getTemperature();
                        textView.setText(String.valueOf(temp));
                    }
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(Actions.LOADED.toString());
        registerReceiver(br, intFilt);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WeatherIntentService.startActionFoo(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        City city = WeatherStorage.getInstance(MainActivity.this)
                .getCurrentCity();
        Weather weather = WeatherStorage.getInstance(MainActivity.this).getLastSavedWeather(city);
        TextView textView = ((TextView) findViewById(R.id.textView));
        Integer temp = weather.getTemperature();
        textView.setText(String.valueOf(temp));
    }
}
