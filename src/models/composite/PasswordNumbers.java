package models.composite;

import models.SignUpModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordNumbers implements FormValidation {
    List<String> error = new ArrayList<>();
    @Override
    public List<String> checkForms(SignUpModel model) {
        Pattern p = Pattern.compile("^(?=.*[0-9]).{1,}$");
        Matcher m = p.matcher(model.getPassWord());
        if(!m.matches()){
            error.add("La contraseña debe tener al menos un número.");
        }
        return error;
    }
}
