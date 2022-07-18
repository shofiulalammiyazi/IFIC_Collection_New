package com.csinfotechbd.customerloanprofile.dailynote;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Data
@Entity
public class DailyNoteEntity extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    /*@Lob*/
    private String note;
    @Expose
    private String pin;
    private String accountNo;


    @Expose
    @Transient
    private String noteFromLobObject;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;

    public String getNoteFromLobObject() {
        return noteFromLobObject;
    }

    public void setNoteFromLobObject(String noteFromLobObject) {
        this.noteFromLobObject = noteFromLobObject;
    }

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public CustomerBasicInfoEntity getCustomerBasicInfo() {
        return customerBasicInfo;
    }

    public void setCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfo) {
        this.customerBasicInfo = customerBasicInfo;
    }
}
