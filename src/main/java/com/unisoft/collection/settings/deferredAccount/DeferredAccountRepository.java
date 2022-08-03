package com.unisoft.collection.settings.deferredAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeferredAccountRepository extends JpaRepository<DeferredAccount, Long> {

    @Query(value = "select * from DEFERRED_ACCOUNT WHERE ACC_NO = ? ", nativeQuery = true)
    DeferredAccount findDeferredAccount(String accountNo);
}
