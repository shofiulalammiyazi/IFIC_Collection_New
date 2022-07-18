package com.csinfotechbd.collection.samd.dataEntry.initiativeActionTaken;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class InitiativeActionTaken extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String initiativeActionTaken;
    private String accountNumber;
    private String dealerId;
}
