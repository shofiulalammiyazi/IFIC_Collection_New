package com.unisoft.collection.settings.dpdBucket;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DPDBucketEntityDto1 {

    private Long id;
    private Integer bucketName;

    public DPDBucketEntityDto1() {
    }

    public DPDBucketEntityDto1(@NotNull DPDBucketEntity dpdBucketEntity) {
        this.id = dpdBucketEntity.getId();
        this.bucketName = Integer.parseInt(dpdBucketEntity.getBucketName());
    }

    public DPDBucketEntityDto1(Long id, Integer bucketName) {
        this.id = id;
        this.bucketName = bucketName;
    }
}
