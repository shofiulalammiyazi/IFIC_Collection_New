package com.unisoft.collection.settings.unpaidInstallmentNumber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnpaidInstallmentNumberRepository extends JpaRepository<UnpaidInstallmentNumber, Long> {
}
