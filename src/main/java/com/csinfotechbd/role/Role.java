package com.csinfotechbd.role;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.user.User;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerModel;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "los_tb_s_roles")
public class Role extends BaseInfo implements PropertyBasedMakerCheckerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @Column(unique = true)
    @NotBlank(message = "Name Cannot be Empty")
    private String name;

    private String description;

    private String remark;

    // 	TODO: Unnecessary code. Delete whenever feel safe
//    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    private List<User> users = new ArrayList<User>();
//
//    public void addUser(User user) {
//        users.add(user);
//        user.getRoles().add(this);
//    }
//
//    public void removeUser(User user) {
//        users.remove(user);
//        user.getRoles().remove(this);
//    }
//
//    public void removeUsers() {
//        for (User u : new ArrayList<>(users)) {
//            removeUser(u);
//        }
//    }

    @Override
    public String toString() {
        return "{"
                + " 'roleId':'" + roleId + "'"
                + ",  'name':'" + name + "'"
                + ",  'description':'" + description + "'"
                + "}";
    }
}
