package com.unisoft.collection.settings.considerAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.contactCategory.ContactCategory;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

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

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ContactCategory> contactCategoryObj;

}
