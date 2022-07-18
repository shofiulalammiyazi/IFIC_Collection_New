package com.csinfotechbd.legal.dataEntry.blaAttendanceHistory;

import com.csinfotechbd.beans.Validation;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BLAAttendanceHistoryService {

    private final BLAAttendanceHistoryRepository blaAttendanceHistoryRepository;

    public void insertData(LitigationCaseInfo previousCase, LitigationCaseInfo currentCase){
        BLAAttendanceHistory lastHistory = blaAttendanceHistoryRepository.findFirstByLitigationCaseInfoIdOrderByCreatedDateDesc(currentCase.getId());
        BLAAttendanceHistory history = new BLAAttendanceHistory();

       /* history.setLitigationCaseInfoId(currentCase.getId());
        history.setLdNo(currentCase.getLdNo());
*/
     /*   history.setNextDateFixed(currentCase.isNextDateFixed());
        history.setNextDate(currentCase.getNextDate());
        history.setCreatedDate(new Date());*/

       /* if (lastHistory != null && lastHistory.getCourseOfAction() == null){
            lastHistory.setCourseOfActionContestedType(currentCase.getCourseOfActionContestedType());
            lastHistory.setCourseOfAction(currentCase.getCourseOfAction());
            lastHistory.setBlaAttendance(currentCase.isBlaAttendance());

            blaAttendanceHistoryRepository.save(lastHistory);
        }
        else{
            history.setCourseOfActionContestedType(currentCase.getCourseOfActionContestedType());
            history.setCourseOfAction(currentCase.getCourseOfAction());
            history.setBlaAttendance(currentCase.isBlaAttendance());
        }

        if (currentCase.getNextDate() != null){
            if (currentCase.getNextDate() != null && previousCase.getNextDate() == null){
                blaAttendanceHistoryRepository.save(history);
            }
            else if (!currentCase.getNextDate().equals(previousCase.getNextDate())){
                blaAttendanceHistoryRepository.save(history);
            }
        }*/

        /*if (previousCase != null && previousCase.getId() != null){
            history.setLitigationCaseInfoId(previousCase.getId());
            history.setLdNo(previousCase.getLdNo());

            history.setNextDateFixed(previousCase.isNextDateFixed());
            history.setNextDate(previousCase.getNextDate());

            history.setCourseOfActionContestedType(currentCase.getCourseOfActionContestedType());
            history.setCourseOfAction(currentCase.getCourseOfAction());

            history.setBlaAttendance(currentCase.isBlaAttendance());

            history.setCreatedDate(new Date());
        }*/

        /*if (previousCase.getId() != null) {
            if (currentCase.getNextDate() != null || previousCase.getNextDate() != null){
                if ((currentCase.getNextDate() == null && previousCase.getNextDate() != null) ||
                        currentCase.getNextDate() != null && previousCase.getNextDate() == null){
                    blaAttendanceHistoryRepository.save(history);
                }
                else if (!currentCase.getNextDate().equals(previousCase.getNextDate())){
                    blaAttendanceHistoryRepository.save(history);
                }
            }
        }*/
    }

    public List<BLAAttendanceHistory> getAllByLitigationCaseInfoId(Long id){
        List<BLAAttendanceHistory> histories = blaAttendanceHistoryRepository.findAllByLitigationCaseInfoIdOrderByCreatedDateDesc(id);
        return histories;
    }

}
