package com.example.leman.lavrinenko_rk_1;

/**
 * Created by Leman on 01.10.16.
 */
public enum Actions {
    LOAD {
        public String toString() {
            return ("ru.mail.park.WEATHER_LOAD_ACTION");
        }
    },
    LOADED {
        public String toString() {
            return ("ru.mail.park.WEATHER_LOADED_ACTION");
        }
    },
}
