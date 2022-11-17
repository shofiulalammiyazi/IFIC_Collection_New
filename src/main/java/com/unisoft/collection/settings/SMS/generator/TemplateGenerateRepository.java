package com.unisoft.collection.settings.SMS.generator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateGenerateRepository extends JpaRepository<TemplateGenerate, Long> {
    TemplateGenerate findTemGenById(Long id);
}


