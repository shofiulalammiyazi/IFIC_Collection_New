package com.csinfotechbd.user;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.role.Role;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerModel;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "los_tb_m_users")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseInfo implements PropertyBasedMakerCheckerModel {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false, unique = true)
    @Expose
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Expose
    @Pattern(regexp = "[a-zA-Z .\\-]+", message = "Please insert valid words")
    @Column(nullable = false)
    private String firstName;

    private boolean loggedIn;

    @Expose
    @Pattern(regexp = "[a-zA-Z .\\-]+", message = "Please insert valid words")
    @Column(nullable = false)
    private String lastName;

    @Column(length = 20, nullable = false)
    private String employeeId;


    @Column(length = 20, nullable = false)
    private String agencyId;

    private Boolean isAgency;

//	@ManyToOne
//	@JoinColumn(name="branch_id",nullable = true)
//	private Branch branch;

    private boolean passReset;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "los_tb_j_users_roles",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roles = new ArrayList<Role>();

    private boolean status;

    private String remark;


    /*
     * LOGIN WRONG PASSWORD SECURITY ENHANCE
     * IMPLEMENTED BY :~: HASIBUL ISLAM. 08-Mar-2021.
     * */
    @Column
    @Expose
    private int loginFailureAttempts;

    @Column
    @Expose
    private int loginLockedAttempts;

    @Column
    @Expose
    private Timestamp lockedTime;

    public User() {
    }

    public User(@NotBlank(message = "First Name cannot be empty") String firstName,
                @NotBlank(message = "Last Name cannot be empty") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void removeRole(Role role) {
        roles.remove(role);
//        role.getUsers().remove(this);
    }

    public void removeAllRoleFromUser() {
        for (Role r : new ArrayList<>(roles)) {
            removeRole(r);
        }
    }

    public void addRole(Role role) {
        roles.add(role);
//        role.getUsers().add(this);
    }

    @Override
    public String toString() {
        return "{"
                + " 'userId':'" + userId + "'"
                + ",  'username':'" + username + "'"
                + ",  'firstName':'" + firstName + "'"
                + ",  'lastName':'" + lastName + "'"
                + ",  'employeeId':'" + employeeId + "'"
                + ",  'agencyId':'" + agencyId + "'"
                + "}";
    }




}
