package com.csinfotechbd.collection.settings.memoNoteCategory;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.user.UserPrincipal;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
public class MemoNoteCategory extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;

    @PrePersist
    public void onCreate(){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setCreatedBy(user.getUsername());
        setCreatedDate(new Date());
    }

    @PreUpdate
    public void onUpdate(){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setModifiedBy(user.getUsername());
        setModifiedDate(new Date());
    }
}
