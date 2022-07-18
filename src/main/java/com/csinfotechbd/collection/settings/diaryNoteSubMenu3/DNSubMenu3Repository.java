package com.csinfotechbd.collection.settings.diaryNoteSubMenu3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DNSubMenu3Repository extends JpaRepository<DiaryNoteSubMenu3Entity, Long> {
    DiaryNoteSubMenu3Entity findByCode(String code);
}
