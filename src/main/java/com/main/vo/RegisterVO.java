package com.main.vo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.Constraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * created by ersya 30/03/2019
 */
@Data
public class RegisterVO {

    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 40,message = "Full name min char is 4 and max char is 40")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 15, message = "Username in char is 3 and max char is 15")
    private String username;

    @NotBlank(message = "Email is required")
    @Size(max = 40, message = "Email max char is 40")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password min char is 6 and max char is 20")
    private String password;

    @NotBlank(message = "Confirmation password is required")
    private String confirmationPassword;
}
