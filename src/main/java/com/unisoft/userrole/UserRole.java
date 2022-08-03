package com.unisoft.userrole;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="los_tb_j_users_roles")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    private Long userId;
    private Integer roleId;

    @Transient
	private String userName;

    @Transient
	private String roleName;
}
