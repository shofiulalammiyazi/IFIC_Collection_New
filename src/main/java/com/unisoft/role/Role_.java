package com.unisoft.role;

import com.unisoft.user.User;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
public class Role_ {

    public static volatile SingularAttribute<Role, Integer> roleId;
    public static volatile SingularAttribute<Role, String> name;
    public static volatile ListAttribute<Role, User> users;
    public static volatile ListAttribute<Role, Boolean> enabled;
}
