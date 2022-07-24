package com.unisoft.collection.settings.businessSegment;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.user.UserPrincipal;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
public class BusinessSegment extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    @OneToOne(cascade = CascadeType.REFRESH)
    private LocationEntity location;


    @PrePersist
    void onCreate(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        setCreatedDate(new Date());
        setCreatedBy(userPrincipal.getUsername());
    }

    @PreUpdate
    void onUpdate(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        setModifiedDate(new Date());
        setModifiedBy(userPrincipal.getUsername());
    }
}
