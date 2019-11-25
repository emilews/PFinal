package models;

import models.composite.SignUpFormVerification;

import java.util.List;

public class Test {


    public static void main(String[] args) {
        SignUpModel s = new SignUpModel();
        s.setPassWord("Hola1230");
        s.setUserName("Wmiloo");
        SignUpFormVerification sv = new SignUpFormVerification();
        List<String> errors = sv.checkForms(s);
        for(String e : errors){
            System.out.println(e);
        }
    }
}
