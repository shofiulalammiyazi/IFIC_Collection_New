package com.csinfotechbd.templatePermission;
/*
Created by Monirul Islam at 9/29/2019
*/

import com.csinfotechbd.role.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
