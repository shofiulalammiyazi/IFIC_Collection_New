package com.unisoft.retail.card.dataEntry.cardreferenceinfo;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.unisoft.base.BaseInfo;

import javax.persistence.*;

@Data
@Entity
public class CardReferenceInfo extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
//    private  Long customer_id;
    private  String ref_account_no;
    private  String ref_name;
    private String ref_occupation;
    private String ref_company_name;
    private String ref_designation;
    private String ref_phone_no;
    private String ref_residence_phone;
    private String ref_office_phone;
    private  String ref_home_address;
    private  String ref_office_address;
    private  String ref_permanent_address;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MMM.yyyy")
    private String ref_dob;
    private String ref_nid_or_passport;
    private String ref_relationship;
    private boolean facility_with_ucb;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;

}
