package com.csinfotechbd.legal.setup.lawyers;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.branch.Branch;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Lawyer extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotBlank(message = "Please enter Lawyer Id")
    private String lawyerId;

    @NotBlank(message = "Please enter name")
    private String name;

    //    @NotBlank(message = "Please enter phone number")
    private String phoneNo;

    //    @NotBlank(message = "Please enter present address")
    private String presentAddress;
    //    @Min(value = 1, message = "Please select a division")
//    private Long division;

    //    @Min(value = 1, message = "Please select a district")
//    private Long district;

    //    @NotNull(message = "Please select a branch")
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "branch_id")
    private List<Branch> branch;

    @Transient
    private List<Integer> branchIds;

    //    @NotBlank(message = "Please enter chember address")
    private String chamberAddress;

    //    @NotBlank(message = "Please enter email address")
//    @Email(message = "Please enter a valid email")
    private String email;

    //    @NotBlank(message = "Please enter mobile number")
    private String mobileNo;

    //    @NotBlank(message = "Please enter Court Jurisdiction")
    private String courtJurisdiction;

    public Lawyer() {
    }

    public Lawyer(Long id) {
        this.id = id;
    }

    public List<Integer> getBranchIds() {
        return branchIds = branch != null ? branch.stream().map(Branch::getBranchId).collect(Collectors.toList()) : new ArrayList<>();
    }

    public void setBranchIds(List<Integer> branchIds) {
        this.branch = branchIds != null ? branchIds.stream().map(Branch::new).collect(Collectors.toList()) : new ArrayList<>();
    }
}

