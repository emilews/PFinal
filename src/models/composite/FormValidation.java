package models.composite;

import models.SignUpModel;

import java.util.List;

public interface FormValidation {
    List<String> checkForms(SignUpModel model);
}
