package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.TownResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static service.Secrets.GEOCODING_API_KEY;

public class CoordToTown {
    public static TownResponse Convert (double latitude, double longitude) throws IOException, InterruptedException {
        String url = "https://geocode.maps.co/reverse?lat=" + latitude + "&lon=" + longitude + "&api_key=" + GEOCODING_API_KEY;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        if (!body.startsWith("{")) {
            System.out.println(body);
            throw new IOException("Error: Too many requests");
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(body, TownResponse.class);
    }
}
