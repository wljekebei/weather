package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import static service.TownToCoord.Convert;

public class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        WeatherResponse res = GetData.fetchData(40.7127281, -74.0060152);
        TownResponse townData = CoordToTown.Convert(res.latitude, res.longitude);

        Label weather = new Label(String.format("Current weather in %s, %s", municipality(townData), townData.address.country));
        weather.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        Label temperature = new Label(String.format("%d %s", Math.round(res.current.temperature_2m), res.current_units.temperature_2m));
        temperature.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 38));

        Label feelsLike = new Label(String.format("Feels like %d %s", Math.round(res.current.apparent_temperature), res.current_units.apparent_temperature));
        feelsLike.setFont(Font.font("Arial", FontWeight.MEDIUM, 24));

        Label wmo = new Label(String.format("%s", getWmoDescription(res.current.weather_code)));
        wmo.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));


        VBox tempBox = new VBox(temperature, feelsLike);
        tempBox.setSpacing(10);
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
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                WeatherResponse resp = null;
                try {
                    if (locationField.getText() != null) {
                        double[] coords = TownToCoord.Convert(locationField.getText());
                        Thread.sleep(1100);
                        resp = GetData.fetchData(coords[0], coords[1]);
                        TownResponse townData = CoordToTown.Convert(resp.latitude, resp.longitude);
                        weather.setText(String.format("Current weather in %s, %s", municipality(townData), townData.address.country));
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
            }
        });

        HBox loc = new HBox(enterLocation, locationField, refreshButton);
        loc.setAlignment(Pos.CENTER);
        loc.setSpacing(20);

        VBox vbox = new VBox(weather, iconView[0], tempBox);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        VBox root = new VBox(vbox, wmo, loc);
        root.setSpacing(50);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Weather");
        stage.setScene(scene);
        stage.show();
    }

    public static String municipality (TownResponse townData) {
        if (townData.address.city == null) {
            if (townData.address.town == null) {
                return townData.address.village;
            } else {
                return townData.address.town;
            }
        } else {
            return townData.address.city;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
