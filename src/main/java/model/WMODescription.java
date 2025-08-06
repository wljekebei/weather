package model;

import java.util.HashMap;
import java.util.Map;

public class WMODescription {
    public static final Map<Integer, String> descMap = new HashMap<>();
    static {
        descMap.put(0, "Clear sky");
        descMap.put(1, "Mainly clear");
        descMap.put(2, "Partly cloudy");
        descMap.put(3, "Overcast");
        descMap.put(45, "Fog");
        descMap.put(48, "Depositing rime fog");
        descMap.put(51, "Light drizzle");
        descMap.put(53, "Moderate drizzle");
        descMap.put(55, "Dense drizzle");
        descMap.put(56, "Light freezing drizzle");
        descMap.put(57, "Dense freezing drizzle");
        descMap.put(61, "Slight rain");
        descMap.put(63, "Moderate rain");
        descMap.put(65, "Heavy rain");
        descMap.put(66, "Light freezing rain");
        descMap.put(67, "Heavy freezing rain");
        descMap.put(71, "Slight snow fall");
        descMap.put(73, "Moderate snow fall");
        descMap.put(75, "Heavy snow fall");
        descMap.put(77, "Snow grains");
        descMap.put(80, "Slight rain showers");
        descMap.put(81, "Moderate rain showers");
        descMap.put(82, "Violent rain showers");
        descMap.put(85, "Slight snow showers");
        descMap.put(86, "Heavy snow showers");
        descMap.put(95, "Thunderstorm: Slight or moderate");
        descMap.put(96, "Thunderstorm with slight hail");
        descMap.put(99, "Thunderstorm with heavy hail");
    }

    public static final Map<Integer, String> iconMap = new HashMap<>();
    static {
        iconMap.put(0, "/icons/sun.png");
        iconMap.put(1, "/icons/mainly-clear.png");
        iconMap.put(2, "/icons/partly-cloudy.png");
        iconMap.put(3, "/icons/overcast.png");
        iconMap.put(45, "/icons/fog.png");
        iconMap.put(48, "/icons/fog.png");
        iconMap.put(51, "/icons/light-drizzle.png");
        iconMap.put(53, "/icons/drizzle.png");
        iconMap.put(55, "/icons/drizzle.png");
        iconMap.put(56, "/icons/freezing-drizzle.png");
        iconMap.put(57, "/icons/freezing-drizzle.png");
        iconMap.put(61, "/icons/slight-rain.png");
        iconMap.put(63, "/icons/slight-rain.png");
        iconMap.put(65, "/icons/heavy-rain.png");
        iconMap.put(66, "/icons/freezing-rain.png");
        iconMap.put(67, "/icons/freezing-rain.png");
        iconMap.put(71, "/icons/slight-snowfall.png");
        iconMap.put(73, "/icons/snowfall.png");
        iconMap.put(75, "/icons/snowfall.png");
        iconMap.put(77, "/icons/slight-snowfall.png");
        iconMap.put(80, "/icons/rain-showers.png");
        iconMap.put(81, "/icons/storm.png");
        iconMap.put(82, "/icons/storm.png");
        iconMap.put(85, "/icons/snow-storm.png");
        iconMap.put(86, "/icons/snow-storm.png");
        iconMap.put(95, "/icons/thunder.png");
        iconMap.put(96, "/icons/thunder.png");
        iconMap.put(99, "/icons/thunder.png");
    }
    

    public static String getWmoDescription (int code) {
        return descMap.getOrDefault(code, "Unknown weather code");
    }

    public static String getIcon (int code) {
        return iconMap.getOrDefault(code, "/icons/sun.png");
    }
}
