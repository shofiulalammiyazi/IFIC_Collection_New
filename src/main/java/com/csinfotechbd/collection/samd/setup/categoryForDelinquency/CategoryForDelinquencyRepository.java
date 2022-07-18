package com.csinfotechbd.collection.samd.setup.categoryForDelinquency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryForDelinquencyRepository extends JpaRepository<CategoryDelinquency, Long> {


    CategoryDelinquency findCategoryForDelinquencyById(Long id);
}
