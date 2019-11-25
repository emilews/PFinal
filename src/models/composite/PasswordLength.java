package models.composite;

import models.SignUpModel;

import java.util.ArrayList;
import java.util.List;

public class PasswordLength implements FormValidation {
    List<String> error = new ArrayList<>();
    @Override
    public List<String> checkForms(SignUpModel model) {
        if(!model.getPassWord().isEmpty()){
            if(model.getPassWord().length() < 8){
                error.add("La contraseña tiene que tener mínimo 8 caracteres.");
            }
        }
        return error;
    }
}
