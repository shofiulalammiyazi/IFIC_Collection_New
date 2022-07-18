package com.csinfotechbd.collection.settings.dpdBucket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DpdBucketRepository extends JpaRepository<DPDBucketEntity, Long> {
    List<DPDBucketEntity> findByEnabledOrderByBucketNameAsc(boolean enabled);
    DPDBucketEntity findFirstByMinDpdLessThanEqualAndMaxDpdGreaterThanEqual(double dpd1, double dpd2);

    DPDBucketEntity findByBucketName(String bucketName);
    boolean existsByBucketName(String bucketName);
}
