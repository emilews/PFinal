package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField UsuarioLogin;
    @FXML
    TextField ContraseñaLogin;
    @FXML
    Button EntrarLogin;
    @FXML
    Button RegistrarLogin;
    @FXML
    Label LabelLogin;

    @FXML
    public void ChangeToRegister() throws IOException {
        App.register();
    }
}
