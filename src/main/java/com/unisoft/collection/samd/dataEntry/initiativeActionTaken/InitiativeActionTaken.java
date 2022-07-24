package com.unisoft.collection.samd.dataEntry.initiativeActionTaken;

import com.unisoft.base.BaseInfo;
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
