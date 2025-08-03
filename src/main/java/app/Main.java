package app;

import model.*;
import service.GetData;

import java.io.IOException;

import static model.WMODescription.getWmoDescription;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        WeatherResponse res = GetData.fetchData();
        System.out.println("Now it is " + Math.round(res.current.temperature_2m) + " " + res.current_units.temperature_2m
            + ". It feels like " + Math.round(res.current.apparent_temperature) + " " + res.current_units.apparent_temperature
            + ". It is " + getWmoDescription(res.current.weather_code) + ".");
    }
}
