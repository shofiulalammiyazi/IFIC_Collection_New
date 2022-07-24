package com.unisoft.collection.settings.assetSubClassificationLoan;
/*
Created by   Islam at 7/16/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity(name = "Asset_Sub_Classification_Loan_Entity")
public class LoanSubClassification extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please enter a name")
    private String name;

    @NotBlank(message = "Please enter a code")
    @Column(unique = true)
    private String code;
}
