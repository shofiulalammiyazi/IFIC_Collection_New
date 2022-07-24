package com.unisoft.collection.samd.dataEntry.cardDistribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SamCardDistributionRepository extends JpaRepository<SamCardDistribution, Long> {

    @Query("FROM SamCardDistribution WHERE id in ?1")
    List<SamCardDistribution> findAllByIds(List<Long> ids);
}
