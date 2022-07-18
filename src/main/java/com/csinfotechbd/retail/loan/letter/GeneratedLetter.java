package com.csinfotechbd.retail.loan.letter;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class GeneratedLetter extends CommonEntity {


    private String accountNo;
    private String referenceId;
    private String date;
    private String latterType;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatterType() {
        return latterType;
    }

    public void setLatterType(String latterType) {
        this.latterType = latterType;
    }
}
