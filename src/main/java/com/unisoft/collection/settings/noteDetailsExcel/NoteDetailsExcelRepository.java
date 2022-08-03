package com.unisoft.collection.settings.noteDetailsExcel;

import com.unisoft.customerloanprofile.dailynote.DailyNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface NoteDetailsExcelRepository extends JpaRepository<DailyNoteEntity,Long> {

    DailyNoteEntity findDailyNoteEntitiesByAccountNo(String accountNo);
}
