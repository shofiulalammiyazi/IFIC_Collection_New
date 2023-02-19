package com.unisoft.collection.settings.contactCategory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.considerAttempt.ConsiderAttempt;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
public class ContactCategory extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contactCategoryName;
    private String considerationAsContact;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "consider_attempt_id")
    @JsonBackReference
    @JsonIgnore
    private ConsiderAttempt considerAttempt;

}
