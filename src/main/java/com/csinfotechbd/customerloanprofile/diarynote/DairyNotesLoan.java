package com.csinfotechbd.customerloanprofile.diarynote;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DairyNotesLoan extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //    @Expose
//    private  Long customer_id;
    @Expose
    private String loan_menu;
    @Expose
    private String loan_menu_id;
    @Expose
    private String loan_submenu_one;
    @Expose
    private String loan_submenu_one_id;
    @Expose
    private String loan_submenu_two;
    @Expose
    private String loan_submenu_two_id;
    @Expose
    private String loan_submenu_three;
    @Expose
    private String loan_submenu_three_id;
    @Expose
    private String loan_time;
    @Expose
    private String loan_date;
    @Expose
    private String loan_short_note;
    @Expose
    private String pin;
    @Expose
    private String username;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;

}
