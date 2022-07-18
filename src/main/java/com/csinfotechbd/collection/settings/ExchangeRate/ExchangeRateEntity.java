package com.csinfotechbd.collection.settings.ExchangeRate;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ExchangeRateEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private Double exchangeRate;
    private Boolean latest;

}
