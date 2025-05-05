package sample;

import sample.DataController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.SerialPortService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Main extends Application {

    private final static int MAX_MOISTURE_LEVEL = 1 << 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var sp = SerialPortService.getSerialPort("COM3");
        var outputStream = sp.getOutputStream();

        var pane = new BorderPane();
        primaryStage.setTitle("set slider to 7 to turn on LED");

        var label = new Label();
        var button = new Button("pump button");

        button.setOnMousePressed(value -> {
            try{
                outputStream.write(8);
            } catch(IOException e){
                e.printStackTrace();
            }
        });
        button.setOnMouseReleased(value -> {
            try {
                outputStream.write(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        var slider = new Slider();
        slider.setMin(0.0);
        slider.setMax(7.0);


        label.textProperty().bind(slider.valueProperty().asString("Slider value is %.0f "));

        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try{
                outputStream.write(newValue.byteValue());

            } catch(IOException e){
                e.printStackTrace();

            }
        });

        pane.setCenter(slider);
        pane.setBottom(label);
        pane.setTop(button);

        pane.setPadding(new Insets(0, 20, 0, 20));

        var scene = new Scene(pane, 400, 200);

        primaryStage.setScene(scene);

        primaryStage.setX(950);    //sets location of the primary stage
        primaryStage.setY(425);    //sets location of the primary stage

        primaryStage.show();

        var controller = new DataController();
        sp.addDataListener(controller);

        var stage = new Stage();

        stage.setTitle("Moisture Level Graph");

        var now = System.currentTimeMillis();


        var xAxis = new NumberAxis("time ", now, now + 50000, 10000);       // creates the x-axis
        var yAxis = new NumberAxis("y", 100, MAX_MOISTURE_LEVEL, 25);   // creates the y-axis

        var series = new XYChart.Series<>(controller.getDataPoints());                                  // creates the series (all the data)
        var lineChart = new LineChart<>(xAxis, yAxis, FXCollections.singletonObservableList(series));  // creates the chart
        lineChart.setTitle("Soil sensor");

        Scene secondScene = new Scene(lineChart,800,600);                       // creates the JavaFX window

        stage.setScene(secondScene);

        stage.setX(100);
        stage.setY(30);

        stage.show();
    }
}



