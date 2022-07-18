package com.csinfotechbd.legal.setup.collateralSecurity;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.csinfotechbd.legal.dataEntry.fileFollowUp.LitigationFileFollowUp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;


@Data
@Entity
@Table(name = "LITIGATION_FILE_FOLLOW_UP_COLLATERAL_SECURITY_DETAILS")
public class FileFollowCollateralSecurityDetails extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "file_follow_up_collateral_security_id")
    @JsonBackReference
    @JsonIgnore
    LitigationFileFollowUp litigationFileFollowUp;

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
    private Double numberOfSchedule;
    private Double collateralTotalLandArea;

}
