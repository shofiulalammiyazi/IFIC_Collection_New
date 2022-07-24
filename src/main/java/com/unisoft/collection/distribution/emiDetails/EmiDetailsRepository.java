package com.unisoft.collection.distribution.emiDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmiDetailsRepository extends JpaRepository<EmiDetails, Long> {

    @Query(value = "select * from EMI_DETAILS", nativeQuery = true)
    List<EmiDetails> findMyDistinct();

    EmiDetails findByCardNo(String cardNo);
}
