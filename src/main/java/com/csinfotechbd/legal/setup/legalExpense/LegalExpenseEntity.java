package com.csinfotechbd.legal.setup.legalExpense;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class LegalExpenseEntity extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String legalExpenseName;

    public LegalExpenseEntity() {
    }

    public LegalExpenseEntity(Long id) {
        this.id = id;
    }


}
