package com.unisoft.collection.settings.manualLetterSetup;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.designation.DesignationEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class ManualLetterSetupInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean accountType= true;

    @Column(unique = true)
    private String letterType;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<DesignationEntity> designation = new ArrayList<>();

    private String refNoPrefix;

    @Transient
    private List<String> designationIds = new ArrayList<>();
}
