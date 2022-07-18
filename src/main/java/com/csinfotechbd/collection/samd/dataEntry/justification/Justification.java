package com.csinfotechbd.collection.samd.dataEntry.justification;

import com.csinfotechbd.base.BaseInfo;
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
