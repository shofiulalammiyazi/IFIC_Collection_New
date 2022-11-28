package com.unisoft.collection.lettertemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterTemplateRepository extends JpaRepository<LetterTemplate, Long> {

    List<LetterTemplate> findAllByUnit(String unit);
}
