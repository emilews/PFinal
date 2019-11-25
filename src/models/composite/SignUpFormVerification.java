package models.composite;

import models.SignUpModel;

import java.util.ArrayList;
import java.util.List;

public class SignUpFormVerification implements FormValidation{
    SignUpModel model;
    List<String> errors = new ArrayList<>();
    List<FormValidation> hojas = new ArrayList<>();
    public SignUpFormVerification(){
        PasswordLength pl = new PasswordLength();
        PasswordLetters pls = new PasswordLetters();
        PasswordNumbers pn = new PasswordNumbers();
        hojas.add(pl);
        hojas.add(pls);
        hojas.add(pn);
    }
    @Override
    public List<String> checkForms(SignUpModel model) {
        for(FormValidation fv : hojas){
            errors.addAll(fv.checkForms(model));
        }
        return errors;
    }
}
