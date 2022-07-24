package com.unisoft.collection.samd.dataEntry.initiativeActionTaken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiativeActionTakenRepository extends JpaRepository<InitiativeActionTaken, Long> {
    List<InitiativeActionTaken> findAllByAccountNumber(String accountNumber);
}
