package com.unisoft.templatePermission;


import com.unisoft.role.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RoleToUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Role role;

    @Transient
    private String[] urlList;

    @Lob
    private String url;
}
