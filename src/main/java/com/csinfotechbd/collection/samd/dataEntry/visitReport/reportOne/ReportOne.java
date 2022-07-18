package com.csinfotechbd.collection.samd.dataEntry.visitReport.reportOne;

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class ReportOne extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String visitDate;
    private String branchName;
    private String purposeOfVisit;
    private String customerId;


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    List<EmployeeInfoEntity> officerMakingVisit;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    List<EmployeeInfoEntity> representativeBranch;


    @Transient
    private List<Long> officerMakingVisitIds;
    @Transient
    private List<Long> representativeBranchIds;


    public void setOfficerMakingVisitIds(List<Long> officerMakingVisitIds) {
        this.officerMakingVisit = officerMakingVisitIds != null ? officerMakingVisitIds.stream().map(EmployeeInfoEntity::new).collect(Collectors.toList()) : null;
    }

    public void setRepresentativeBranchIds(List<Long> representativeBranchIds) {
        this.representativeBranch = representativeBranchIds != null ? representativeBranchIds.stream().map(EmployeeInfoEntity::new).collect(Collectors.toList()) : null;
    }
}
