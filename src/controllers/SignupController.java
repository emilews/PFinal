package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.SignUpModel;
import models.composite.SignUpFormVerification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupController {
    @FXML
    TextField RegUsuario;
    @FXML
    PasswordField RegContra;
    @FXML
    PasswordField RegContra2;
    @FXML
    Button RegAtras;
    @FXML
    Button RegEntrar;
    @FXML
    Label RegErrors;

    @FXML
    public void ChangeBack() throws IOException {
        App.loginScreen();
    }
    @FXML
    public void register(){
        RegErrors.setText("");
        List<String> errors = new ArrayList<>();
        if(RegUsuario.getText().trim().isEmpty()){
            errors.add("El campo de usuario debe estar lleno.");
        }
        if(RegContra.getText().trim().isEmpty()){
            errors.add("El campo de contraseña tiene que estar lleno.");
        }
        if(RegContra2.getText().trim().isEmpty()){
            errors.add("Repita la contraseña, por favor.");
        }
        if(!RegContra.getText().trim().equals(RegContra2.getText().trim())){
            errors.add("Las contraseñas tienen que ser iguales.");
        }
        SignUpModel user = new SignUpModel();
        user.setUserName(RegUsuario.getText().trim());
        user.setPassWord(RegContra.getText().trim());
        SignUpFormVerification sv = new SignUpFormVerification();
        errors.addAll(sv.checkForms(user));
        if(!errors.isEmpty()){
            for(String s : errors){
                RegErrors.setText(RegErrors.getText()+"\n"+s);
            }
        }else{
            MainController.setClienteModel(user.getUserName(), App.getIP(), App.getPort(),
                    user.getPassWord(), true);
        }
    }
}
