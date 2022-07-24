package com.unisoft.collection.settings.considerAttempt;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ConsiderAttempt extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String attemptName;
    private String contactType;
    private String considerContact;
    private String contactCategory;
    @ElementCollection
    private List<String> type;

}
