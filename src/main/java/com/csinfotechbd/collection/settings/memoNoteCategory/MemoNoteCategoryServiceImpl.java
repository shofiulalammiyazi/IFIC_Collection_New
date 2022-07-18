package com.csinfotechbd.collection.settings.memoNoteCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoNoteCategoryServiceImpl implements MemoNoteCategoryService {
    @Autowired
    MemoNoteCategoryRepository memoNoteCategoryRepository;
    @Override
    public void save(MemoNoteCategory memoNoteCategory) {
        memoNoteCategoryRepository.save(memoNoteCategory);
    }

    @Override
    public List<MemoNoteCategory> getList() {
        return memoNoteCategoryRepository.findAll();
    }

    @Override
    public MemoNoteCategory findById(Long id) {
        Optional<MemoNoteCategory> optMemoNoteCategory = memoNoteCategoryRepository.findById(id);
        if(optMemoNoteCategory.isPresent())
            return optMemoNoteCategory.get();
        else
            return null;
    }
}
