package core;

import controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class App extends Application {
    public static Stage primaryStage;
    private static String IP = "localhost";
    private static int Port = 9000;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                MainController.salir();
                System.exit(0);
            }
        });
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
    public static Stage getStage() {
        return primaryStage;
    }


    public static String getIP(){
        return IP;
    }
    public static int getPort(){
        return Port;
    }

}
