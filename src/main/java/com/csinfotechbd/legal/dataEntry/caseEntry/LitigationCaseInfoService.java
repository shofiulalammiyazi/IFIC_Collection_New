package com.csinfotechbd.legal.dataEntry.caseEntry;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface LitigationCaseInfoService {

    void save(LitigationCaseInfo litigationCaseInfo);

    List<String> saveCaseInfoFromExcel(MultipartFile file);

    List<LitigationCaseInfo> findAllLitigationCaseInfo();

    List<LitigationCaseInfoDto> findAllActiveHeadOffice();
    List<LitigationCaseInfoDto> findActiveSuitsWithCommonColumns(String getBranch);

    LitigationCaseInfo getLitigationCaseInfo(Long id);

    List<LitigationCaseInfo> getByCourseOfAction(Long courseOfActionId);

    List<LitigationCaseInfo> getBetweenNextDate(Date fromDate, Date toDate);

    List<LitigationCaseInfo> getNotificationForMissingNextDateOrPreviousDate();

    List<LitigationCaseInfo> getLitigationCaseInfoByCusAccNum(String accNum);

    List<LitigationCaseInfo> getLitigationRevisions(Long id);

    List<String> getLawyerChangeHistory(Long id);

    List<String> getPlaintiffChangeHistory(Long id);
    List<String> getPlaintiffChangeHistoryDate(Long id);


    List<LitigationCaseInfo> getListForExpiredNextDate();
}
