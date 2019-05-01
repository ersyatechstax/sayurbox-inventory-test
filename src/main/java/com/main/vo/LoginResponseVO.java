package com.main.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * created by ersya 30/03/2019
 */
@Data
@AllArgsConstructor
public class LoginResponseVO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("full_name")
    private String fullName;

    private String username;

    private String email;

    private String role;

}
