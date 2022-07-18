package com.csinfotechbd.collection.samd.setup.categoryForDelinquency;


import java.util.List;

public interface CategoryForDelinquencyService {
    List<CategoryDelinquency> findAll();

    void save(CategoryDelinquency categoryForDelinquency);

    CategoryDelinquency findCategoryForDelinquencyById(Long id);
}
