package com.unisoft.role;

import lombok.Data;

@Data
public class RoleDto {

    private Integer roleId;

    private String name;

    public RoleDto(Role role) {
        roleId = role.getRoleId();
        name = role.getName();
    }
}
