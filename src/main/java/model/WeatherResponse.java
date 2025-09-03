package model;

import java.util.List;

public class WeatherResponse {
    public double latitude;
    public double longitude;
    public double generationtime_ms;
    public int utc_offset_seconds;
    public String timezone;
    public String timezone_abbreviation;
    public double elevation;
    public Units current_units;
    public Current current;
    public HourlyUnits hourly_units;
    public Hourly hourly;
    public DailyUnits daily_units;
    public Daily daily;

    public static class Units {
        public String time;
        public String interval;
        public String temperature_2m;
        public String apparent_temperature;
        public String weather_code;
    }

    public static class Current {
        public String time;
        public int interval;
        public double temperature_2m;
        public double apparent_temperature;
        public int weather_code;
    }

    public static class HourlyUnits {
        public String time;
        public String temperature_2m;
        public String apparent_temperature;
        public String weather_code;
    }

    public static class Hourly {
        public List <String> time;
        public List <Double> temperature_2m;
        public List <Double> apparent_temperature;
        public List <Integer> weather_code;
    }

    public static class DailyUnits {
        public String time;
        public String weather_code;
        public String temperature_2m_max;
        public String temperature_2m_min;
        public String temperature_2m_mean;
    }

    public static class Daily {
        public List <String> time;
        public List <Integer> weather_code;
        public List <Double> temperature_2m_max;
        public List <Double> temperature_2m_min;
        public List <Double> temperature_2m_mean;
    }
}