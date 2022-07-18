package com.csinfotechbd.user;

import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.employeeStatus.EmployeeStatusEntity;
import com.csinfotechbd.role.Role;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, Long> userId;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> employeeId;
    public static volatile SingularAttribute<User, Boolean> enabled;
    public static volatile SingularAttribute<User, Branch> branch;
    public static volatile SingularAttribute<User, EmployeeStatusEntity> status;
    public static volatile ListAttribute<User, Role> roles;
}
