package com.csinfotechbd.customerloanprofile.letterinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterInformationRepository extends JpaRepository<LetterInformation, Long> {
    List<LetterInformation> findByAccountNo(String accountNo);
}
