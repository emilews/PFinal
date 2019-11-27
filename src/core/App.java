package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("DiscordiaChat");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void register() throws IOException {
        Parent reg = FXMLLoader.load(App.class.getResource("../views/signup.fxml"));
        primaryStage.getScene().setRoot(reg);
    }
    public static void loginScreen() throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("../views/login.fxml"));
        primaryStage.getScene().setRoot(root);
    }
    public static void mainScreen() throws IOException {
        Parent main = FXMLLoader.load(App.class.getResource("../views/main.fxml"));
        primaryStage.getScene().setRoot(main);
    }
}
