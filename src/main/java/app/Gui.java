package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.TownResponse;
import model.WeatherResponse;
import service.CoordToTown;
import service.GetData;
import service.TownToCoord;

import java.io.IOException;

import static model.WMODescription.getIcon;
import static model.WMODescription.getWmoDescription;

public class Gui extends Application {
    private VBox day0, day1, day2, day3;
    private VBox todayAfter, yestTomor;
    private HBox forecast, central;

    @Override
    public void start(Stage stage) throws Exception {
        WeatherResponse res = GetData.fetchData(40.7127281, -74.0060152);
        TownResponse townData = CoordToTown.Convert(res.latitude, res.longitude);

        Label weather = new Label(String.format("%s, %s", municipality(townData, "New York"), townData.address.country));
        weather.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        Label temperature = new Label(String.format("%d %s", Math.round(res.current.temperature_2m), res.current_units.temperature_2m));
        temperature.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 38));

        Label feelsLike = new Label(String.format("Feels like %d %s", Math.round(res.current.apparent_temperature), res.current_units.apparent_temperature));
        feelsLike.setFont(Font.font("Arial", FontWeight.MEDIUM, 24));

        Label wmo = new Label(String.format("%s", getWmoDescription(res.current.weather_code)));
        wmo.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));


        VBox tempBox = new VBox(temperature, feelsLike);
        tempBox.setSpacing(5);
        tempBox.setAlignment(Pos.CENTER);

        String iconPath = getIcon(res.current.weather_code);
        Image weatherIcon = new Image(getClass().getResourceAsStream(iconPath));
        final ImageView[] iconView = {new ImageView(weatherIcon)};
        iconView[0].setFitWidth(120);
        iconView[0].setFitHeight(120);

        TextField locationField = new TextField();
        locationField.setFont(Font.font("Arial", FontWeight.MEDIUM, 22));

        Label enterLocation = new Label("Enter location: ");
        enterLocation.setFont(Font.font("Arial", FontWeight.MEDIUM, 22));

        Button refreshButton = new Button("Refresh");
        refreshButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 22));
        refreshButton.setDefaultButton(true);
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                WeatherResponse resp = null;
                try {
                    if (locationField.getText() != null) {
                        double[] coords = TownToCoord.Convert(locationField.getText());
                        Thread.sleep(1100);
                        resp = GetData.fetchData(coords[0], coords[1]);
                        TownResponse townData = CoordToTown.Convert(resp.latitude, resp.longitude);
                        weather.setText(String.format("%s, %s", municipality(townData, locationField.getText()), townData.address.country));
                    }
                } catch (IOException | InterruptedException ex) {
                    weather.setText(ex.getMessage());
                }
                assert resp != null;
                temperature.setText(String.format("%d %s", Math.round(resp.current.temperature_2m), resp.current_units.temperature_2m));
                feelsLike.setText(String.format("Feels like %d %s", Math.round(resp.current.apparent_temperature), resp.current_units.apparent_temperature));
                wmo.setText(String.format("%s", getWmoDescription(resp.current.weather_code)));
                String iconPath1 = getIcon(resp.current.weather_code);
                iconView[0].setImage(new Image(getClass().getResourceAsStream(iconPath1)));

                yestTomor.getChildren().clear();
                todayAfter.getChildren().clear();
                day0 = dailyForecast(resp, 0);
                day1 = dailyForecast(resp, 1);
                day2 = dailyForecast(resp, 2);
                day3 = dailyForecast(resp, 3);
                yestTomor.getChildren().addAll(day0, day2);
                todayAfter.getChildren().addAll(day1, day3);

            }
        });

        HBox loc = new HBox(enterLocation, locationField, refreshButton);
        loc.setAlignment(Pos.CENTER);
        loc.setSpacing(20);

        Label now = new Label("NOW");
        now.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        VBox currentWeather = new VBox(now, iconView[0], tempBox, wmo);
        currentWeather.setSpacing(20);
        currentWeather.setAlignment(Pos.CENTER);
        currentWeather.setPadding(new Insets(0, 0, 0, 30));

        day0 = dailyForecast (res, 0);
        day1 = dailyForecast (res, 1);
        day2 = dailyForecast (res, 2);
        day3 = dailyForecast (res, 3);

        yestTomor = new VBox(day0, day2);
        yestTomor.setSpacing(40);
        todayAfter = new VBox(day1, day3);
        todayAfter.setSpacing(40);

        forecast = new HBox(yestTomor, todayAfter);
        forecast.setSpacing(65);

        central = new HBox(currentWeather, forecast);
        central.setSpacing(100);
        central.setAlignment(Pos.CENTER);

        VBox root = new VBox(weather, central, loc);
        root.setSpacing(50);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 650, 580);
        stage.setTitle("Weather");
        stage.setScene(scene);
        stage.show();
    }

    public static String municipality (TownResponse townData, String request) {
        if (townData.address.city == null) {
            if (townData.address.town == null) {
                if (townData.address.village == null) {
                    return request;
                } else {
                    return townData.address.village;
                }
            } else {
                return townData.address.town;
            }
        } else {
            return townData.address.city;
        }
    }

    public static VBox dailyForecast (WeatherResponse res, int day) {
        String strDay = res.daily.time.get(day);
        Label date = new Label(String.format("%c%c.%c%c", strDay.charAt(8), strDay.charAt(9), strDay.charAt(5), strDay.charAt(6)));
        date.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label temperature = new Label(String.format("%d° / %d°", Math.round(res.daily.temperature_2m_min.get(day)), Math.round(res.daily.temperature_2m_max.get(day))));
        temperature.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

        Label wmo = new Label(String.format("%s", getWmoDescription(res.daily.weather_code.get(day))));
        wmo.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));

        String iconPath = getIcon(res.daily.weather_code.get(day));
        Image weatherIcon = new Image(Gui.class.getResourceAsStream(iconPath));
        final ImageView[] iconView = {new ImageView(weatherIcon)};
        iconView[0].setFitWidth(60);
        iconView[0].setFitHeight(60);

        VBox vbox = new VBox(date, iconView[0], temperature, wmo);
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    public static void main(String[] args) {
        launch();
    }
}
