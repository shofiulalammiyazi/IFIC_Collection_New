package com.unisoft.collection.settings.considerAttempt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsiderAttemptRepository extends JpaRepository<ConsiderAttempt, Long> {

    List<ConsiderAttempt> findByEnabled(boolean enabled);
    List<ConsiderAttempt>findByTypeAndEnabled(String type,boolean enabled);

}
