package com.example.leman.lavrinenko_rk_1;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class WeatherIntentService extends IntentService {

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(Actions.LOAD.toString())) {
                City city = WeatherStorage.getInstance(WeatherIntentService.this)
                        .getCurrentCity();
                try {
                    Weather weather = WeatherUtils.getInstance().loadWeather(city);
                    if (weather != null) {
                        WeatherStorage.getInstance(WeatherIntentService.this).saveWeather(city, weather);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent((Actions.LOADED.toString())));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
