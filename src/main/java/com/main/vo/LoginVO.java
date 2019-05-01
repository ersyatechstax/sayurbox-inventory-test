package com.main.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * created by ersya 30/03/2019
 */
@Data
public class LoginVO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}
