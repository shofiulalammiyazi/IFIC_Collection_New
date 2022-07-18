package com.csinfotechbd.collection.samd.dataEntry.borrowerPresentProposition;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BorrowerPresentProposition extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String borrowerPresentProposition;
    private String accountNumber;
    private String dealerId;

}
