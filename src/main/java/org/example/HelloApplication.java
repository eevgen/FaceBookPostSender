package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sending-add-to.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 484);

            Image image = new Image("/icons/FacebookSender.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.setTitle("FacebookSender");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        launch();

    }

}