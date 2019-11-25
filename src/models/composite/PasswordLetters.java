package models.composite;

import javafx.css.Match;
import models.SignUpModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordLetters implements FormValidation {
    List<String> error = new ArrayList<>();
    @Override
    public List<String> checkForms(SignUpModel model) {
        Pattern p = Pattern.compile("^(?=.*[A-Z]).{1,}$");
        Matcher m = p.matcher(model.getPassWord());
        Pattern p2 = Pattern.compile("^(?=.*[a-z]).{1,}$");
        Matcher m2 = p2.matcher(model.getPassWord());
        if(!m.matches()){
            error.add("La contraseña debe tener al menos una mayúscula.");
        }
        if(!m2.matches()){
            error.add("La contraseña debe tener al menos una minúscula.");
        }
        return error;
    }
}
