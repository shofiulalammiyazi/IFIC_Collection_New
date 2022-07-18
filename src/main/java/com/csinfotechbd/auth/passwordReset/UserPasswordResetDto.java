package com.csinfotechbd.auth.passwordReset;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Setter
@Getter
public class UserPasswordResetDto {

    private String username;

    @NotBlank(message = "Password can not be blank!")
    @Size(min = 8, max = 20, message = "Password length must be between 8 to 20 character!")
    private String password;

    @NotBlank(message = "Password can not be blank!")
    @Size(min = 8, max = 20, message = "Passwords must match!")
    private String confirmPassword;
}
