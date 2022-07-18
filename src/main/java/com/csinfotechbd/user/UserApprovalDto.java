package com.csinfotechbd.user;

import lombok.Data;

@Data
public class UserApprovalDto {

    private String username;

    private String firstName;

    private String lastName;

    private String remark;

    private boolean enabled;

    private String createdBy;

    private String modifiedBy;

    public UserApprovalDto() {
    }

    public UserApprovalDto(User user) {
        if (user == null) return;
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        remark = user.getRemark();
        enabled = user.isEnabled();
        createdBy = user.getCreatedBy();
        modifiedBy = user.getModifiedBy();
    }

}
