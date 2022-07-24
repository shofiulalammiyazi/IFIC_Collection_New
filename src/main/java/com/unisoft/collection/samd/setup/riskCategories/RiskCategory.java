package com.unisoft.collection.samd.setup.riskCategories;


import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.unit.UnitEntity;
import com.unisoft.common.CommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
public class RiskCategory extends CommonEntity {

    private String riskCategoryName;
    private Boolean fid = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private UnitEntity unit;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<DPDBucketEntity>dpdBucketEntities = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<AgeCodeEntity>ageCodeEntities = new ArrayList<>();

    @Transient
    private List<Long>dpdBucketIds;

    @JsonIgnore
    @Transient
    private List<Long>ageCodeIds;

    @Transient
    private Long unitId;


    public UnitEntity getUnit() {
        return unit = (unit != null) ? unit: new UnitEntity();
    }

    public void setUnitId(Long unitId) {
        this.unit = new UnitEntity(unitId);
    }

    public void setDpdBucketIds(List<Long> dpdBucketIds) {
        this.dpdBucketEntities = dpdBucketIds != null ? dpdBucketIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList()) : null;
    }

    public void setAgeCodeIds(List<Long> ageCodeIds) {
        this.ageCodeEntities = ageCodeIds != null ? ageCodeIds.stream().map(AgeCodeEntity::new).collect(Collectors.toList()) : null;
    }
}
