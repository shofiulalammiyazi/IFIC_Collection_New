package com.unisoft.collection.samd.dataEntry.backgroundOfTheBorrower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackgroundOfTheBorrowerRepository extends JpaRepository<BackgroundOfTheBorrower,Long> {
    List<BackgroundOfTheBorrower> findAllByAccountNumber(String accountNumber);
}
