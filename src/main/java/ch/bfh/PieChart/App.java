package ch.bfh.PieChart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PieChart.fxml"));
        stage.setTitle("Group 33");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}