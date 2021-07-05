package ru.geekbrains.june.chat.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();  // ссылка на контроллер
        controller.setStage(primaryStage);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("WindBlwg Chat Client");
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
