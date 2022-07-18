package com.csinfotechbd.userrole;

import com.csinfotechbd.role.Role;
import com.csinfotechbd.user.User;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleDto {


    private Long userId;
    private String username;
    private List<Role> roles;

    public UserRoleDto() {
    }

    public UserRoleDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        roles = user.getRoles();
    }
}
