package com.csinfotechbd.collection.accountescalation;

import com.csinfotechbd.common.CommonEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Data
public class AccountEscalationNote extends CommonEntity {

    private String note;
    private String status;

    @ManyToOne
    @JsonBackReference
    private AccountEscalation accountEscalation;

    @Transient
    private Long escalationId;

    public AccountEscalationNote() {
    }

    public AccountEscalationNote(String note) {
        this.note = note;
    }

    public AccountEscalationNote(String note, String status, AccountEscalation accountEscalation, Long escalationId) {
        this.note = note;
        this.status = status;
        this.accountEscalation = accountEscalation;
        this.escalationId = escalationId;
    }
}
