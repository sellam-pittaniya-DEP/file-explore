package lk.ijse.dep12.image_explorer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainView.fxml"))));
//        primaryStage.setResizable(false);
        primaryStage.setTitle("Image Explorer");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
