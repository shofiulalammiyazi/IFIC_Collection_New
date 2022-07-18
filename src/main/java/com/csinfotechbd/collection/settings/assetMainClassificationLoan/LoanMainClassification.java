package com.csinfotechbd.collection.settings.assetMainClassificationLoan;
/*
Created by Monirul Islam at 7/16/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity(name = "Asset_Main_Classification_Loan_Entity")
public class LoanMainClassification extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please enter name")
    private String name;

    //@NotBlank(message = "Please enter code")
    //@Column(unique = true)
    private String code;
}
