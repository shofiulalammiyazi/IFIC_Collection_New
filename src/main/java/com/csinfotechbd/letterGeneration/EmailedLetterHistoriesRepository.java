package com.csinfotechbd.letterGeneration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailedLetterHistoriesRepository extends JpaRepository<EmailedLetterHistories, Long> {

    List<EmailedLetterHistories> findByCustomerIdOrderByCreatedDateDesc(Long customerId);
}
