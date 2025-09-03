package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.WeatherResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetData {
    public static WeatherResponse fetchData (double lat, double lon) throws IOException, InterruptedException {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&daily=weather_code,temperature_2m_max,temperature_2m_min,temperature_2m_mean&hourly=temperature_2m,apparent_temperature,weather_code&current=temperature_2m,apparent_temperature,weather_code&timezone=auto&past_days=1&forecast_days=3";
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(url))
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(body, WeatherResponse.class);
    }
}
