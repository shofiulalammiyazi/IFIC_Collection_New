package com.csinfotechbd.retail.card.dataEntry.dairynotes;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DairyNotes extends BaseInfo {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Expose
//    private Long customer_id;
    @Expose
    private String card_menu;
    @Expose
    private String card_menu_id;
    @Expose
    private String card_submenu_one;
    @Expose
    private String card_submenu_one_id;
    @Expose
    private String card_submenu_two;
    @Expose
    private String card_submenu_two_id;
    @Expose
    private String card_submenu_three;
    @Expose
    private String card_submenu_three_id;
    @Expose
    private String card_time;
    @Expose
    private String card_date;
    @Expose
    private String card_short_note;

    @Expose
    private String pin;
    @Expose
    private String username;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;
}
