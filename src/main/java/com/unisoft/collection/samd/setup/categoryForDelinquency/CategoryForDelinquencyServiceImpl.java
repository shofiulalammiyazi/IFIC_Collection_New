package com.unisoft.collection.samd.setup.categoryForDelinquency;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryForDelinquencyServiceImpl implements CategoryForDelinquencyService{


    @Autowired
    private CategoryForDelinquencyRepository categoryForDelinquencyRepository;


    @Override
    public List<CategoryDelinquency> findAll() {
        return categoryForDelinquencyRepository.findAll();
    }

    @Override
    public void save(CategoryDelinquency categoryForDelinquency) {
            categoryForDelinquencyRepository.save(categoryForDelinquency);
    }

    @Override
    public CategoryDelinquency findCategoryForDelinquencyById(Long id) {
        return categoryForDelinquencyRepository.findCategoryForDelinquencyById(id);
    }
}
