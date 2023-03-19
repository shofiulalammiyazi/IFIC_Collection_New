package com.unisoft.collection.settings.branch;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.branch.api.BranchDetails;
import com.unisoft.collection.settings.district.DistrictEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@Table(name = "los_tb_s_branch")
@EntityListeners(AuditingEntityListener.class)
public class Branch extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer branchId;

//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinColumn(name = "district_id")
//    private DistrictEntity district;

    //    @Column(unique = true) //unique for legal case report purpose
    @NotBlank(message = "Branch name cannot be empty")
    private String branchName;

    private String address;

    private String routingNo;

    private String contactPerson;

    private String primaryPhoneNo;

    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Branch Code Cannot be Empty")
    private String branchCode;

    private String MNEMONIC;
    private String CABAD1;
    private String CABAD2;
    private String CABAD3;
    private String CABAD4;
    private String CABAD5;
    private String BRANCHBOOTH;

//    private String secondayPhoneNo;
//
//    private String designation;
//
//    private String branchStatus;
//
//    private String isRequired;

    public Branch() {
    }

    public Branch(Integer id) {
        branchId = id;
    }

    public Branch(String branchCode, String branchName) {
        this.branchCode = branchCode;
        this.branchName = branchName;
    }

//    public Branch(BranchDetails branchDetails) {
//        this.branchName = branchDetails.;
//        this.address = address;
//        this.routingNo = routingNo;
//        this.contactPerson = contactPerson;
//        this.primaryPhoneNo = primaryPhoneNo;
//        this.email = email;
//        this.branchCode = branchCode;
//    }

    /**
     * Used for generating Json objects without using any third party library
     * Caution: Implementation within Thymeleaf CDATA block may produce unparsable data
     *
     * @return Json string of the branch instance
     */
    @Override
    public String toString() {
        return "{'branchId':" + branchId + ","
                + "'branchName':'" + branchName + "',"
                + "'branchCode':'" + branchCode + "'"
                +"}";
    }


}
