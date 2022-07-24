package com.unisoft.collection.settings.dpdBucket;
/*
Created by   Islam on 7/7/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DPDBucketEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String bucketName;
    private Double minDpd;
    private Double maxDpd;
    //    private Double exactDpd;
//    private boolean includeMax = false;
//    private boolean includeMin = false;
    private boolean interim = false;

    public DPDBucketEntity() {
    }

    public DPDBucketEntity(Long id) {
        this.id = id;
    }

    public DPDBucketEntity(String id) {
        this.id = Long.parseLong(id);
    }
}
