package com.unisoft.retail.loan.setup.parReleaseLoan;

/*
 * Created by Yasir Araphat on 25 April, 2021
 */

import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class ParReleaseLoan extends CommonEntity {

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "PAR_RELEASE_LOAN_ID") // Creates a column named 'PAR_RELEASE_LOAN_ID' in DPD_BUCKET_ENTITY Table
    private List<DPDBucketEntity> dpdBucketList;

    @Transient
    private List<Long> dpdBucketIds;

    public List<String> getDpdBucketNames() {
        return dpdBucketList == null ? new ArrayList<>() : dpdBucketList.stream().map(DPDBucketEntity::getBucketName).collect(Collectors.toList());
    }

    public List<Long> getDpdBucketIds() {
        return dpdBucketList == null ? new ArrayList<>() : dpdBucketList.stream().map(DPDBucketEntity::getId).collect(Collectors.toList());
    }

    public void setDpdBucketIds(List<Long> dpdBucketIds) {
        this.dpdBucketList = dpdBucketIds.stream().map(DPDBucketEntity::new).collect(Collectors.toList());
    }
}
