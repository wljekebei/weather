package model;

import java.util.HashMap;
import java.util.Map;

public class WMODescription {
    public static final Map<Integer, String> map = new HashMap<>();
    static {
        map.put(0, "Clear sky");
        map.put(1, "Mainly clear");
        map.put(2, "Partly cloudy");
        map.put(3, "Overcast");
        map.put(45, "Fog");
        map.put(48, "Depositing rime fog");
        map.put(51, "Light drizzle");
        map.put(53, "Moderate drizzle");
        map.put(55, "Dense drizzle");
        map.put(56, "Light freezing drizzle");
        map.put(57, "Dense freezing drizzle");
        map.put(61, "Slight rain");
        map.put(63, "Moderate rain");
        map.put(65, "Heavy rain");
        map.put(66, "Light freezing rain");
        map.put(67, "Heavy freezing rain");
        map.put(71, "Slight snow fall");
        map.put(73, "Moderate snow fall");
        map.put(75, "Heavy snow fall");
        map.put(77, "Snow grains");
        map.put(80, "Slight rain showers");
        map.put(81, "Moderate rain showers");
        map.put(82, "Violent rain showers");
        map.put(85, "Slight snow showers");
        map.put(86, "Heavy snow showers");
        map.put(95, "Thunderstorm: Slight or moderate");
        map.put(96, "Thunderstorm with slight hail");
        map.put(99, "Thunderstorm with heavy hail");
    }

    public static String getWmoDescription (int code) {
        return map.getOrDefault(code, "Unknown weather code");
    }
}
