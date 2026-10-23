package com.samuel.provasamuel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class HelloApplication extends Application {
    @Override public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 900, 650);
        stage.setTitle("Estoque de Medicamentos");
        stage.setScene(scene);
        stage.show();
    }
}
