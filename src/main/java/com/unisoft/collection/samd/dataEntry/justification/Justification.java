package com.unisoft.collection.samd.dataEntry.justification;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Justification extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String justification;
    private String accountNumber;
    private String dealerId;
}
