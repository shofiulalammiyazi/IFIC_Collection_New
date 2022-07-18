package com.csinfotechbd.common;

import com.csinfotechbd.user.UserService;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.boot.actuate.audit.listener.AuditListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditListener.class)
public class CommonEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    @Column(name = "created_date")
    private Date createdDate;

    @Expose
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean deleted;

    private String remark;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedDate(new Date());
        this.setCreatedBy(UserService.getSessionUsername());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setModifiedDate(new Date());
        this.setModifiedBy(UserService.getSessionUsername());
    }
}
