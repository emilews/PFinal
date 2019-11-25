package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class app extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("DiscordiaChat");
        stage.setScene(scene);
        stage.show();
    }
}
