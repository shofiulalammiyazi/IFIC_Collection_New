package com.unisoft.retail.loan.letter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedLetterRepository extends JpaRepository<GeneratedLetter, Long> {
}
