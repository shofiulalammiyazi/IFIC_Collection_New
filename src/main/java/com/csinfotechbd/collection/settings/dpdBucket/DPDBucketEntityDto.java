package com.csinfotechbd.collection.settings.dpdBucket;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DPDBucketEntityDto {


    private Long id;
    private String bucketName;

    public DPDBucketEntityDto() {
    }

    public DPDBucketEntityDto(@NotNull DPDBucketEntity dpdBucketEntity) {
        this.id = dpdBucketEntity.getId();
        this.bucketName = dpdBucketEntity.getBucketName();
    }

    public DPDBucketEntityDto(Long id, String bucketName) {
        this.id = id;
        this.bucketName = bucketName;
    }
}
