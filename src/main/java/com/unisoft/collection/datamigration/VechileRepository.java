package com.unisoft.collection.datamigration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VechileRepository extends JpaRepository<Vehcile, Long> {
    Vehcile findByAccountNo(String accountNo);
}
