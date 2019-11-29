package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Cliente;

import java.io.IOException;
import java.sql.Connection;

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
    @FXML
    public void logIn(){
        boolean can = true;
        if(UsuarioLogin.getText().trim().isEmpty()){
            can = false;
            //LabelLogin.setText("Por favor ingrese un usuario.");
        }
        if(ContraseñaLogin.getText().trim().isEmpty()){
            can = false;
            //LabelLogin.setText("Por favor ingrese una contraseña.");
        }
        if(can){
            MainController.setClienteModel(UsuarioLogin.getText().trim(),
                    App.getIP(), App.getPort(),
                    ContraseñaLogin.getText().trim(), false);
        }
    }
}
