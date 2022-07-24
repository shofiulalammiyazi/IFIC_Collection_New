package com.unisoft.collection.finalSattlementAmount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalSattlementAmountRepository extends JpaRepository<FinalSattlementAmount, Long> {

    @Query("FROM FinalSattlementAmount WHERE customerId = ?1")
    FinalSattlementAmount getByCustomerId(long customerId);
}
