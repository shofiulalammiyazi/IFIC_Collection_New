package com.unisoft.collection.samd.dataEntry.borrowerPresentProposition;

import com.unisoft.base.BaseInfo;
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
