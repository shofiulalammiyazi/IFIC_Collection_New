package com.csinfotechbd.user;

import com.csinfotechbd.role.RoleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private Long userId;

    private String username;

    private String firstName;

    private String lastName;

    private String oldPassword;

    private String password;

    private String pass2;

    List<RoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        if (user.getRoles() != null && !user.getRoles().isEmpty())
            roles = user.getRoles().stream().map(RoleDto::new).collect(Collectors.toList());
    }

}
