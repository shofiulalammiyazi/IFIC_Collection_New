package com.csinfotechbd.retail.card.dataEntry.dailyNotes;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DailyNotesCard extends BaseInfo {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    @Lob
    private String note;
    @Expose
    private String name;

    @Expose
    @Transient
    private String noteFromLobObject;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteFromLobObject() {
        return noteFromLobObject;
    }

    public void setNoteFromLobObject(String noteFromLobObject) {
        this.noteFromLobObject = noteFromLobObject;
    }

    public CustomerBasicInfoEntity getCustomerBasicInfo() {
        return customerBasicInfo;
    }

    public void setCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfo) {
        this.customerBasicInfo = customerBasicInfo;
    }
}
