package com.unisoft.collection.settings.memoNoteCategory;

import java.util.List;

public interface MemoNoteCategoryService {

    public void save(MemoNoteCategory memoNoteCategory);

    public List<MemoNoteCategory> getList();

    MemoNoteCategory findById(Long id);
}
