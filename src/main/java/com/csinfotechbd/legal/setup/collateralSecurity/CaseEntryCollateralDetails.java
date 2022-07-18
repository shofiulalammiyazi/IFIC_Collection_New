package com.csinfotechbd.legal.setup.collateralSecurity;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class CaseEntryCollateralDetails extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "litigation_case_info_id")
    @JsonBackReference
    @JsonIgnore
    private LitigationCaseInfo litigationCaseInfo;

    //@Transient
    private Long collateralSecurityId;

    private String collateralSecurityName;
    //new fields for multiple value

    private String collateralSecurityFixedAssetDetails;
    private String collSecurityCityCorporation;
    private String collSecurityPourasava;
    private String outOfCityCorporationPourasava;
    private String collSecurityDistrict;
    private String collSecurityMouza;
    private String collSecuritySro;
    private String collSecurityPs;
    private String khatianCs;
    private String khatianSa;
    private String khatianRs;
    private String khatianBs;
    private String khatianCity;
    private String jlCs;
    private String jlSa;
    private String jlRs;
    private String jlBs;
    private String jlCity;
    private String plotCs;
    private String plotSa;
    private String plotRs;
    private String plotBs;
    private String plotCity;
    private String boundedByNorth;
    private String boundedBySouth;
    private String boundedByEast;
    private String boundedByWest;
    private String otherCollateralSecurity;

}
