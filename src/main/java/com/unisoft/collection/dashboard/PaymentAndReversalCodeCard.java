package com.unisoft.collection.dashboard;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class PaymentAndReversalCodeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String description;
    private boolean isPayment;
    private boolean isReversal;
    private String type;
}
