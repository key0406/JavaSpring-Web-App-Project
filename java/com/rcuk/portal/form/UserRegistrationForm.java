package com.rcuk.portal.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationForm {

    @Email(message = "Please enter a valid email address")
    @Length(min = 3, message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Please enter a password")
    @Length(min = 8, message = "Password should be a minimum 8 characters long")
    private String password;

    @NotBlank(message = "Please enter a first name")
    private String fname;

    @NotBlank(message = "Please enter a last name")
    private String lname;

    @NotBlank(message = "Please enter a mobile number")
    @Length(min = 11, message = "Mobile should be 11 digits")
    private String phone;


}
