package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.GeoLocation;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static service.Secrets.GEOCODING_API_KEY;

public class TownToCoord {
    public static double [] Convert (String address) throws IOException, InterruptedException {
        address = address.replace(" ", "-");
        String url = "https://geocode.maps.co/search?q=" + address + "&api_key=" + GEOCODING_API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Type listType = new TypeToken<List<GeoLocation>>(){}.getType();
        List<GeoLocation> locations = gson.fromJson(body, listType);

        if (locations.isEmpty()) {
            throw new IllegalArgumentException("Address not found: " + address);
        }
        double [] coords = {
                Double.parseDouble(locations.getFirst().lat),
                Double.parseDouble(locations.getFirst().lon)
        };

        return coords;
    }
}
