package com.csinfotechbd.role;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class RoleDto {

    private Integer roleId;

    private String name;

    public RoleDto(Role role) {
        roleId = role.getRoleId();
        name = role.getName();
    }
}
