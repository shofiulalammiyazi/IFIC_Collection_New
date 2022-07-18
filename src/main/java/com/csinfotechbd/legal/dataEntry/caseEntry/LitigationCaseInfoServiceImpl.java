package com.csinfotechbd.legal.dataEntry.caseEntry;

import ch.qos.logback.core.pattern.color.BlackCompositeConverter;
import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.legal.dataEntry.blaAttendanceHistory.BLAAttendanceHistory;
import com.csinfotechbd.legal.dataEntry.blaAttendanceHistory.BLAAttendanceHistoryService;
import com.csinfotechbd.legal.setup.caseStatus.CaseStatus;
import com.csinfotechbd.legal.setup.caseStatus.CaseStatusService;
import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.legal.setup.collateralSecurity.CaseEntryCollateralDetails;
import com.csinfotechbd.legal.setup.collateralSecurity.CollateralSecurity;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfAction;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfActionService;
import com.csinfotechbd.legal.setup.courts.Courts;
import com.csinfotechbd.legal.setup.courts.CourtsService;
import com.csinfotechbd.legal.setup.lawyers.Lawyer;
import com.csinfotechbd.legal.setup.lawyers.LawyerService;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneEntity;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneService;
import com.csinfotechbd.user.UserService;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.ExcelFileUtils;
import com.csinfotechbd.utillity.StringUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LitigationCaseInfoServiceImpl implements LitigationCaseInfoService {

    private final LitigationCaseInfoRepository litigationCaseInfoRepository;
    private final CaseTypeService caseTypeService;
    private final CourseOfActionService courseOfActionService;
    private final CaseStatusService caseStatusService;
    private final CourtsService courtsService;
    private final BranchService branchService;
    private final LitigationZoneService zoneService;
    private final LawyerService lawyerService;
    private final AuditTrailService auditTrailService;
    private final DateUtils dateUtils;
    private final StringUtils stringUtils;
    private final ExcelFileUtils excelFileUtils;
    private final BLAAttendanceHistoryService blaAttendanceHistoryService;

    @Override
    public void save(LitigationCaseInfo litigationCaseInfo) {
        boolean isNewEntity = false;
        LitigationCaseInfo previousEntity = new LitigationCaseInfo();
        if (litigationCaseInfo.getId() != null) {
            LitigationCaseInfo oldEntity = litigationCaseInfoRepository.getOne(litigationCaseInfo.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            LitigationCaseInfo prevLitigationCaseInfo = getLitigationCaseInfo(litigationCaseInfo.getId());
            litigationCaseInfo.setCreatedDate(prevLitigationCaseInfo.getCreatedDate());
            litigationCaseInfo.setCreatedBy(prevLitigationCaseInfo.getCreatedBy());
            litigationCaseInfo.getBlaAttendanceHistoryList().forEach(item ->{
                item.setLitigationCaseInfoId(litigationCaseInfo.getId());
                item.setModifiedDate(litigationCaseInfo.getModifiedDate());
                item.setModifiedBy(litigationCaseInfo.getModifiedBy());
            });
            litigationCaseInfo.getRecoveryBeforeCaseDetailsList().forEach(item ->{
                item.setModifiedDate(litigationCaseInfo.getModifiedDate());
                item.setModifiedBy(litigationCaseInfo.getModifiedBy());
            });
            litigationCaseInfo.getRecoveryAfterCaseDetailsList().forEach(item ->{
                item.setModifiedDate(litigationCaseInfo.getModifiedDate());
                item.setModifiedBy(litigationCaseInfo.getModifiedBy());
            });
            litigationCaseInfo.getMarketValueSalesValueDetailsList().forEach(item ->{
                item.setLitigationCaseInfoIds(litigationCaseInfo.getId());
                item.setModifiedDate(litigationCaseInfo.getModifiedDate());
                item.setModifiedBy(litigationCaseInfo.getModifiedBy());
            });
            litigationCaseInfo.getLitigationCaseInfoAccountDetailsList().forEach(item ->{
                LitigationCaseInfo litigationCaseInfo1 = new LitigationCaseInfo();
                litigationCaseInfo1.setId(litigationCaseInfo.getId());
                litigationCaseInfo1.setBranchCode(litigationCaseInfo.getBranchCode());
                litigationCaseInfo1.setBranchName(litigationCaseInfo.getBranchName());
                litigationCaseInfo1.setLdNo(litigationCaseInfo.getLdNo());
                litigationCaseInfo1.setZone(litigationCaseInfo.getZone());
                litigationCaseInfo1.setCustomerCifNo(litigationCaseInfo.getCustomerCifNo());
                litigationCaseInfo1.setCustomerAccNum(litigationCaseInfo.getCustomerAccNum());
                item.setModifiedDate(litigationCaseInfo.getModifiedDate());
                item.setModifiedBy(litigationCaseInfo.getModifiedBy());

                item.setLitigationCaseInfo(litigationCaseInfo1);
            });
        } else {
            // TODO: Initial LD number 3000 considered. Fix this later being confirmed by client
//            if (litigationCaseInfo.getLdNo() == null) {
//                long ldNo = litigationCaseInfoRepository.findMaxLdNo(3000L) + 1;
//                litigationCaseInfo.setLdNo(ldNo);
//            }
            isNewEntity = true;
        }




        litigationCaseInfo.setNextDate(litigationCaseInfo.getBlaAttendanceHistoryList().get(litigationCaseInfo.getBlaAttendanceHistoryList().size()-1).getNextDate());

        if (litigationCaseInfo.getBlaAttendanceHistoryList().size() > 1 ){
            litigationCaseInfo.setPreviousDate(litigationCaseInfo.getBlaAttendanceHistoryList().get(litigationCaseInfo.getBlaAttendanceHistoryList().size() - 2).getNextDate());
        }

        litigationCaseInfo.setCourseOfActionIds(litigationCaseInfo.getBlaAttendanceHistoryList().get(litigationCaseInfo.getBlaAttendanceHistoryList().size()-1).getCourseOfActionId());
        litigationCaseInfo.setCourseOfActionNames(litigationCaseInfo.getBlaAttendanceHistoryList().get(litigationCaseInfo.getBlaAttendanceHistoryList().size()-1).getCourseOfActionName());

        litigationCaseInfoRepository.save(litigationCaseInfo);

        litigationCaseInfo.getBlaAttendanceHistoryList().forEach(item ->{
            item.setLitigationCaseInfoId(litigationCaseInfo.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationCaseInfo.getCreatedBy());
        });

        litigationCaseInfo.getRecoveryBeforeCaseDetailsList().forEach(item ->{
            //item.setLitigationCaseInfoId(litigationCaseInfo.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationCaseInfo.getCreatedBy());
        });

        litigationCaseInfo.getRecoveryAfterCaseDetailsList().forEach(item ->{
            //item.setLitigationCaseInfoId(litigationCaseInfo.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationCaseInfo.getCreatedBy());
        });



        litigationCaseInfo.getMarketValueSalesValueDetailsList().forEach(item ->{
            item.setLitigationCaseInfoIds(litigationCaseInfo.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationCaseInfo.getCreatedBy());
        });

        litigationCaseInfo.getCollateralSecurityList().forEach(item -> {
            LitigationCaseInfo litigationCaseInfo1 = new LitigationCaseInfo();
            litigationCaseInfo1.setId(litigationCaseInfo.getId());
            item.setLitigationCaseInfo(litigationCaseInfo1);
        });

        //litigationCaseInfo.getLitigationCaseInfoAccountDetailsList().add(new LitigationCaseInfoAccountDetails());
        litigationCaseInfo.getLitigationCaseInfoAccountDetailsList().forEach(item ->{
            LitigationCaseInfo litigationCaseInfo1 = new LitigationCaseInfo();

            litigationCaseInfo1.setId(litigationCaseInfo.getId());
            item.setBranchCode(litigationCaseInfo.getBranchCode());
            item.setBranchName(litigationCaseInfo.getBranchName());
            item.setLdNo(litigationCaseInfo.getLdNo());
            item.setZone(litigationCaseInfo.getZone());
            item.setCustomerCifNo(litigationCaseInfo.getCustomerCifNo());
            item.setCustomerAccNum(litigationCaseInfo.getCustomerAccNum());

            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationCaseInfo.getCreatedBy());

            item.setLitigationCaseInfo(litigationCaseInfo1);
        });



        if (isNewEntity)
            auditTrailService.saveCreatedData("Case Entry", litigationCaseInfo);
        else
            auditTrailService.saveUpdatedData("Case Entry", previousEntity, litigationCaseInfo);
       // blaAttendanceHistoryService.insertData(previousEntity, litigationCaseInfo);
    }

    @Override
    public List<LitigationCaseInfo> findAllLitigationCaseInfo() {
        return preventNullValues(litigationCaseInfoRepository.findAll());
    }

    @Override
    public List<LitigationCaseInfoDto> findAllActiveHeadOffice() {
        return litigationCaseInfoRepository.getActiveHeadOfficeList().stream().map(LitigationCaseInfoDto::new).collect(Collectors.toList());
    }

    @Override
    public List<LitigationCaseInfoDto> findActiveSuitsWithCommonColumns(String getBranch) {
        return litigationCaseInfoRepository.getActiveListWithCommonColumns(getBranch).stream().map(LitigationCaseInfoDto::new).collect(Collectors.toList());
    }

    @Override
    public LitigationCaseInfo getLitigationCaseInfo(Long id) {
        return litigationCaseInfoRepository.findById(id).orElse(new LitigationCaseInfo());
    }

    @Override
    public List<LitigationCaseInfo> getByCourseOfAction(Long courseOfActionId) {
        return preventNullValues(litigationCaseInfoRepository.findByCourseOfActionId(courseOfActionId));
    }

    @Override
    public List<LitigationCaseInfo> getBetweenNextDate(Date fromDate, Date toDate) {
        return preventNullValues(litigationCaseInfoRepository.findBetweenNextDate(fromDate, toDate));
    }

    @Override
    public List<LitigationCaseInfo> getNotificationForMissingNextDateOrPreviousDate() {
        return preventNullValues(litigationCaseInfoRepository.findByNextOrPreviousDateNull());
    }

    @Override
    public List<LitigationCaseInfo> getLitigationCaseInfoByCusAccNum(String accNum) {
        return preventNullValues(litigationCaseInfoRepository.findByCustomerAccNum(accNum));
    }

    @Override
    public List<LitigationCaseInfo> getLitigationRevisions(Long id) {
        Revisions<Integer, LitigationCaseInfo> litigationRevisionLists = litigationCaseInfoRepository.findRevisions(id);
        return preventNullValues(litigationRevisionLists.stream().map(Revision::getEntity).collect(Collectors.toList()));
    }

    private List<LitigationCaseInfo> preventNullValues(List<LitigationCaseInfo> caseList) {
        caseList.forEach(suit -> {
            if (suit.getLdNo() == null) suit.setLdNo(" ");
        });
        return caseList;
    }

    @Override
    public List<String> getLawyerChangeHistory(Long id) {
        List<String> lawyerIdList = litigationCaseInfoRepository.getLawyerChangeHistory(id);
        return lawyerIdList;
    }

    @Override
    public List<String> getPlaintiffChangeHistory(Long id) {
        List<Tuple> allPlaintiffChanges = litigationCaseInfoRepository.getPlaintiffChangeHistory(id);
        List<String> plaintiffChanges = new LinkedList<>();
        String lastPlaintiff = "";
        for (Tuple plaintiffChange : allPlaintiffChanges) {
            String plaintiffName = Objects.toString(plaintiffChange.get("PLAINTIFF_NAME"), "");
            String date = Objects.toString(plaintiffChange.get("MODIFICATION_DATE"), "");
            // Only where plaintiff is changed
            if (!lastPlaintiff.equals(plaintiffName)) {
                lastPlaintiff = plaintiffName;
                plaintiffChanges.add(plaintiffName + " -> " + date);
            }
        }
        return plaintiffChanges;
    }

    @Override
    public List<String> getPlaintiffChangeHistoryDate(Long id) {
        List<Tuple> allPlaintiffChanges = litigationCaseInfoRepository.getPlaintiffChangeHistory(id);
        List<String> plaintiffChanges = new LinkedList<>();
        String lastPlaintiff = "";
        for (Tuple plaintiffChange : allPlaintiffChanges) {
            String plaintiffName = Objects.toString(plaintiffChange.get("PLAINTIFF_NAME"), "");
            String date = Objects.toString(plaintiffChange.get("MODIFICATION_DATE"), "");
            // Only where plaintiff is changed
            if (!lastPlaintiff.equals(plaintiffName)) {
                lastPlaintiff = plaintiffName;
                plaintiffChanges.add(date);
            }
        }
        return plaintiffChanges;
    }

    @Override
    public List<LitigationCaseInfo> getListForExpiredNextDate() {
        Date expiredDate = dateUtils.getNextOrPreviousDate(new Date(), -3);
        return litigationCaseInfoRepository.findByExpiredNextDate(dateUtils.getStartingPointOfDay(expiredDate));
    }

    @Override
    public List<String> saveCaseInfoFromExcel(MultipartFile file) {
        Map<String, Object> cache = new HashMap<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            int totalSheets = xssfWorkbook.getNumberOfSheets();

            for (int i = 0; i < totalSheets; i++)
                extractXssfSheet(xssfWorkbook.getSheetAt(i), cache);
//            litigationCaseInfoRepository.saveAll((List<LitigationCaseInfo>) cache.get("list"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<String>) cache.get("errors");
    }

    @SuppressWarnings("unchecked")
    private void extractXssfSheet(XSSFSheet table, Map<String, Object> cache) {

        List<LitigationCaseInfo> caseList = (List<LitigationCaseInfo>) cache.getOrDefault("list", new LinkedList<>());
        Map<String, CaseType> caseTypes = (Map<String, CaseType>) cache.getOrDefault("caseTypes", new HashMap<>());
        Map<Long, Map<String, Courts>> caseTypeCourtMap = (Map<Long, Map<String, Courts>>) cache.getOrDefault("caseTypeCourtMap", new HashMap<>());
        List<String> errors = (List<String>) cache.getOrDefault("errors", new LinkedList<>());
        CourseOfAction otherCourseOfAction = (CourseOfAction) cache.getOrDefault("otherCourseOfAction", courseOfActionService.findByNameLike("Others"));
        CaseStatus pendingStatus = caseStatusService.findByNameLike("Pending");

        LitigationCaseInfo suit;
        Iterator<Row> rows = table.iterator();

        // Skip rows untill the header row found and collect cell headers and indices
        Map<Integer, String> headerColumns = new HashMap<Integer, String>() {{
            put(1, "SL#");
            put(2, "Branch Code");
            put(3, "Branch Name");
            put(4, "Region");
        }};
        Map<String, Integer> headers = excelFileUtils.getColumnHeadersAndIndices(rows, headerColumns);



        while (rows.hasNext()) {
            try {
                Row row = rows.next();


                suit = new LitigationCaseInfo();



                String branchCode = excelFileUtils.getTextCellValue(row, headers, "Branch Code", errors).split("\\.")[0];
                //String branchName = excelFileUtils.getTextCellValue(row, headers, "Branch Name", errors);


                // Escape the empty rows
//                if (branchCode.isEmpty() && branchName.isEmpty()) continue;

                String zoneName = excelFileUtils.getTextCellValue(row, headers, "Region", errors);
                String courtJurisdiction = excelFileUtils.getTextCellValue(row, headers, "Court's Jurisdiction", errors);
                String caseTypeName = excelFileUtils.getTextCellValue(row, headers, "Case Type", errors);
                String ldNo = excelFileUtils.getTextCellValue(row, headers, "LD number", errors);
//                Long ldNo = ldNoString.matches("")Long.parseLong(ldNoString);
                String businessSegment = excelFileUtils.getTextCellValue(row, headers, "Business Segment", errors);
                String customerId = excelFileUtils.getTextCellValue(row, headers, "Customer ID No (CIF)", errors);
                String accountNumber = excelFileUtils.getTextCellValue(row, headers, "Loan Account No", errors);
                String accountName = excelFileUtils.getTextCellValue(row, headers, "Loan Account Name", errors);
                String plaintiffName = excelFileUtils.getTextCellValue(row, headers, "Plaintiff/ Petitioner's Name", errors);
                String plaintiffDesignation = excelFileUtils.getTextCellValue(row, headers, "Plaintiff/ Petitioner's Designation", errors);
                String plaintiffPhone = excelFileUtils.getTextCellValue(row, headers, "Plaintiff/ Petitioner's Phone No", errors);
                String defendantName = excelFileUtils.getTextCellValue(row, headers, "Defendant/ Accused Name", errors);
                String defendantPhone = excelFileUtils.getTextCellValue(row, headers, "Defendant/ Accused Active Phone No", errors);
                Date filingDate = excelFileUtils.getDateCellValue(row, headers, "Filing Date (dd.mm.yy)", errors);

                String caseNumber = excelFileUtils.getTextCellValue(row, headers, "Case Number", errors);
                String[] caseNumberAndYear = caseNumber.split("/");
                String caseYear = caseNumberAndYear.length > 1 ? getFormatedCaseYear(caseNumberAndYear[1]) : dateUtils.getFormattedDateString(filingDate, "yyyy");
                caseYear = StringUtils.hasText(caseNumber) ? dateUtils.getFormattedDateString(filingDate, "yyyy") : caseYear;
                Double suitValue = excelFileUtils.getNumberCellValue(row, headers, "Case Claim Amount (Fig. in BDT)", errors).doubleValue();
                String courtName = excelFileUtils.getTextCellValue(row, headers, "Name of the Court", errors);
                Date previousDate = excelFileUtils.getDateCellValue(row, headers, "Previous Date (dd.mm.yy)", errors);
                String actionOfPreviousDate = excelFileUtils.getTextCellValue(row, headers, "Case Status of Previous Date", errors);
                Date nextDate = excelFileUtils.getDateCellValue(row, headers, "Next Date (dd.mm.yy)", errors);
                String actionOfNextDate = excelFileUtils.getTextCellValue(row, headers, "Case Status of Next Date", errors);
                Double legalExpense = excelFileUtils.getNumberCellValue(row, headers, "Total Legal Expense incurred till date", errors).doubleValue();
                String lawyerName = excelFileUtils.getTextCellValue(row, headers, "Lawyer's Name", errors);
                String lawyerPhone = excelFileUtils.getTextCellValue(row, headers, "Lawyer's Active Phone No", errors);
                String writPetitionStatus = excelFileUtils.getTextCellValue(row, headers, "Writ-Petition Status", errors);
                String remarks = excelFileUtils.getTextCellValue(row, headers, "Remarks (If any)", errors);
                String comment = excelFileUtils.getTextCellValue(row, headers, "Comment And Its Impact On The Bank", errors);
                String natureOfWrit = excelFileUtils.getTextCellValue(row, headers, "Nature of writ (Artharin, NI Act, CIB, others)", errors);
                String byWhomeFiled = excelFileUtils.getTextCellValue(row, headers, "By Whom Filed", errors);
                String subjectMatter = excelFileUtils.getTextCellValue(row, headers, "Subject Matter of the case", errors);
                String oppositeParty = excelFileUtils.getTextCellValue(row, headers, "Opposite Party", errors);

                //by shanto
                String recoveryAmtY2017 = excelFileUtils.getTextCellValue(row, headers, "Recovery in Y-2017 (Fig. in BDT)", errors);
                String recoveryAmtY2018 = excelFileUtils.getTextCellValue(row, headers, "Recovery in Y-2018 (Fig. in BDT)", errors);
                String recoveryAmtY2019 = excelFileUtils.getTextCellValue(row, headers, "Recovery in Y-2019 (Fig. in BDT)", errors);


                // Get relational data
                CaseType caseType = getCaseTypeByName(caseTypeName, caseTypes);
                if (caseType == null || caseType.getName() == null)
                    continue;
                String otherCaseType = caseType.getName() != null && caseType.getName().equals("Others") ? caseTypeName : "";

                String branchName = "";
                Branch branch = null;

                if(excelFileUtils.getTextCellValue(row, headers, "Branch Name", errors).equals("Card Division")) {
                    //branchName = "Card Division";
                    branchCode = "1000";
                }

                branch = branchService.getByNumericBranchCode(branchCode);
                branchName = branch != null ? branch.getBranchName() : "";
                branchCode = branch != null ? branch.getBranchCode() : "";
                String district = StringUtils.hasText(branchName) && branch.getDistrict() != null ? branch.getDistrict().getName() : "";
                String division = StringUtils.hasText(district) ? branch.getDistrict().getDivision().getDivName() : "";
                LitigationZoneEntity zone = zoneService.findByNameMatches(zoneName);
                zoneName = zone == null ? "" : zone.getName();

                Courts court = findCourtsByNameLike(courtName, caseType.getId(), caseTypeCourtMap);
                String otherCourt = court != null && court.getName() != null && court.getName().equals("Others") ? courtName : "";
                List<Lawyer> lawyers = findLawyersByNameAndPhoneNumber(lawyerName.split(","), lawyerPhone.split(","), courtJurisdiction); //[\W]+
                //List<Lawyer> lawyers = findLawyersByNameAndPhoneNumber(lawyerName.substring(0,lawyerName.contains(",") == true ? lawyerName.indexOf(",") : lawyerName.length()), lawyerPhone, courtJurisdiction);

                BLAAttendanceHistory blaAttendanceHistory1 = new BLAAttendanceHistory();
                BLAAttendanceHistory blaAttendanceHistory2 = new BLAAttendanceHistory();
                List<BLAAttendanceHistory> blaAttendanceHistoryList1 = new ArrayList<>();
                List<BLAAttendanceHistory> blaAttendanceHistoryList2 = new ArrayList<>();





                // Set all collected data
                suit.setLdNo(ldNo);
                suit.setBranchCode(branchCode);
                suit.setBranchName(branchName);
                suit.setDistrict(district);
                suit.setDivision(division);
                suit.setZone(zoneName);
                suit.setCourtJurisdiction(courtJurisdiction);
                suit.setCaseType(caseType);
                suit.setCaseTypeSubType(natureOfWrit);
                suit.setOtherCaseType(otherCaseType);
                suit.setCaseFiled(caseType.getCaseFiledType());
                suit.setCaseFiledSubType(caseType.getCaseFiledSubType());
                suit.setBusinessSegment(businessSegment);
                if(branchName.equals("Card Division"))
                    suit.setClientId(customerId);
                else
                    suit.setCustomerCifNo(customerId);

                suit.setCustomerAccNum(accountNumber);
                suit.setNameOfAcc(accountName);
                suit.setPlaintiffName(plaintiffName);
                suit.setPlaintiffDesignation(plaintiffDesignation);
                suit.setPlaintiffPhoneNo(plaintiffPhone);
                suit.setDefendantName(defendantName);
                suit.setDefendantPhone(defendantPhone);
                suit.setAccusedName(defendantName);
                suit.setDateOfFiling(formateDate(filingDate));
                suit.setCaseNumber(caseNumber);
                /*suit.setLegalExpense(legalExpense);
                suit.setLegalExpenseDate(new Date());*/

                suit.setCaseYear(caseYear);

                suit.setSuitValue(suitValue);

                if (caseTypeName.equalsIgnoreCase("NI Act")) {
                    suit.setChequeAmount(suitValue);
                }
                suit.setCourt(court);
                suit.setOtherCourt(otherCourt);
                suit.setNextDate(nextDate);
                suit.setPreviousDate(previousDate);
               // suit.setNextDateFixed(previousDate != null);
                //suit.setNextDate(previousDate);
                blaAttendanceHistory1.setNextDate(previousDate);
                blaAttendanceHistory1.setNextDateFixed(previousDate != null);

                blaAttendanceHistory1.setCreatedDate(new Date());
                blaAttendanceHistory1.setCreatedBy(UserService.getSessionUsername());
                blaAttendanceHistory1.setBlaAttendance(false);

                if(caseTypeName.equalsIgnoreCase("suit")||
                        caseTypeName.equalsIgnoreCase("artharin")||
                        caseTypeName.equalsIgnoreCase("artharin suit")){
                    CourseOfAction courseOfAction = courseOfActionService.findByNameLike(actionOfPreviousDate);
                    blaAttendanceHistory1.setCourseOfActionId(courseOfAction.getId());
                    blaAttendanceHistory1.setCourseOfActionName(courseOfAction.getName());
                    blaAttendanceHistory1.setCourseOfActionContestedType(courseOfAction.getContestedType());
                }

                blaAttendanceHistoryList1.add(blaAttendanceHistory1);
                suit.setBlaAttendanceHistoryList(blaAttendanceHistoryList1);

               // setCourseOfAction(suit, actionOfPreviousDate, otherCourseOfAction);
                suit.setLawyer(lawyers);
                suit.setWritPetitionStatus(writPetitionStatus);
                suit.setRemarks(remarks);
                suit.setCommentImpactOnBank(comment);
                suit.setNatureOfWrit(natureOfWrit);
                suit.setByWhomFiled(byWhomeFiled);
                suit.setSubjectMatterOfCase(subjectMatter);
                suit.setOppositePartyName(oppositeParty);
                suit.setStatus(pendingStatus);
                suit.setTotalLegalExpenseAmount(legalExpense);

                suit.setCreatedBy(UserService.getSessionUsername());
                suit.setCreatedDate(new Date());

                // Save information for previous date
                litigationCaseInfoRepository.save(suit);

                blaAttendanceHistory1.setLitigationCaseInfoId(suit.getId());
                blaAttendanceHistory2.setLitigationCaseInfoId(suit.getId());

                // Save information for next date
               /* suit.setNextDateFixed(nextDate != null);
                suit.setNextDate(nextDate);*/
                blaAttendanceHistory2.setNextDate(nextDate);
                blaAttendanceHistory2.setNextDateFixed(nextDate != null);

                blaAttendanceHistory1.setBlaAttendance(false);
                if(caseTypeName.equalsIgnoreCase("suit") ||
                        caseTypeName.equalsIgnoreCase("artharin suit") ||
                        caseTypeName.equalsIgnoreCase("artharin")){
                    CourseOfAction courseOfAction2 = courseOfActionService.findByNameLike(actionOfNextDate);

                    blaAttendanceHistory2.setCourseOfActionId(courseOfAction2.getId());
                    blaAttendanceHistory2.setCourseOfActionName(courseOfAction2.getName());
                    blaAttendanceHistory2.setCourseOfActionContestedType(courseOfAction2.getContestedType());

                    suit.setCourseOfActionNames(courseOfAction2.getName());
                    suit.setCourseOfActionIds(courseOfAction2.getId());
                }

                blaAttendanceHistory2.setCreatedDate(new Date());
                blaAttendanceHistory2.setCreatedBy(UserService.getSessionUsername());

                blaAttendanceHistoryList2.add(blaAttendanceHistory2);
                suit.getBlaAttendanceHistoryList().add(blaAttendanceHistory2);

                List<RecoveryAfterCaseDetails> recoveryAfterCaseDetailsList = new ArrayList<>();
                double totalRecoveryAmt = 0.0;
                if(!recoveryAmtY2017.isEmpty() && !Double.isNaN(Double.parseDouble(recoveryAmtY2017.replace(",","").trim()))){
                    LocalDate givenDate = LocalDate.of(2017, 12, 31);
                    Date date = java.sql.Date.valueOf(givenDate);

                    RecoveryAfterCaseDetails recoveryAfterCaseDetails = new RecoveryAfterCaseDetails();

                    recoveryAfterCaseDetails.setRecoveryAfterCaseAmount(Double.parseDouble(recoveryAmtY2017.replace(",","").trim()));
                    recoveryAfterCaseDetails.setRecoveryAfterCaseDate(formateDate(date));
                    recoveryAfterCaseDetails.setCreatedDate(new Date());
                    recoveryAfterCaseDetails.setCreatedBy(UserService.getSessionUsername());

                    recoveryAfterCaseDetailsList.add(recoveryAfterCaseDetails);
                    totalRecoveryAmt+=Double.parseDouble(recoveryAmtY2017.replace(",","").trim());
                }

                if(!recoveryAmtY2018.isEmpty() && !Double.isNaN(Double.parseDouble(recoveryAmtY2018.replace(",","").trim()))){
                    LocalDate givenDate = LocalDate.of(2018, 12, 31);
                    Date date = java.sql.Date.valueOf(givenDate);

                    RecoveryAfterCaseDetails recoveryAfterCaseDetails = new RecoveryAfterCaseDetails();

                    recoveryAfterCaseDetails.setRecoveryAfterCaseAmount(Double.parseDouble(recoveryAmtY2018.replace(",","").trim()));
                    recoveryAfterCaseDetails.setRecoveryAfterCaseDate(formateDate(date));
                    recoveryAfterCaseDetails.setCreatedDate(new Date());
                    recoveryAfterCaseDetails.setCreatedBy(UserService.getSessionUsername());

                    recoveryAfterCaseDetailsList.add(recoveryAfterCaseDetails);
                    totalRecoveryAmt+=Double.parseDouble(recoveryAmtY2018.replace(",","").trim());
                }

                if(!recoveryAmtY2019.isEmpty() && !Double.isNaN(Double.parseDouble(recoveryAmtY2019.replace(",","").trim()))){
                    LocalDate givenDate = LocalDate.of(2019, 12, 31);
                    Date date = java.sql.Date.valueOf(givenDate);

                    RecoveryAfterCaseDetails recoveryAfterCaseDetails = new RecoveryAfterCaseDetails();

                    recoveryAfterCaseDetails.setRecoveryAfterCaseAmount(Double.parseDouble(recoveryAmtY2019.replace(",","").trim()));
                    recoveryAfterCaseDetails.setRecoveryAfterCaseDate(formateDate(date));
                    recoveryAfterCaseDetails.setCreatedDate(new Date());
                    recoveryAfterCaseDetails.setCreatedBy(UserService.getSessionUsername());

                    recoveryAfterCaseDetailsList.add(recoveryAfterCaseDetails);
                    totalRecoveryAmt+=Double.parseDouble(recoveryAmtY2019.replace(",","").trim());
                }

                suit.setRecoveredAmount(totalRecoveryAmt);
                suit.setRecoveryAfterCaseDetailsList(recoveryAfterCaseDetailsList);

               // setCourseOfAction(suit, actionOfNextDate, otherCourseOfAction);
                suit.setModifiedBy(UserService.getSessionUsername());
                suit.setModifiedDate(new Date());

//                caseList.add(suit);
                litigationCaseInfoRepository.save(suit);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cache.put("list", caseList);
        cache.put("caseTypes", caseTypes);
        cache.put("caseTypeCourtMap", caseTypeCourtMap);
        cache.put("errors", errors);
        cache.put("otherCourseOfAction", otherCourseOfAction);
    }

    public String getFormatedCaseYear(String caseYear) {
        caseYear = caseYear.replaceAll("\\D", "");
        String caseYearPattern = caseYear.replaceAll("\\d", "y");
        Date date = dateUtils.getFormattedDate(caseYear, caseYearPattern);
        if (date == null) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year < 1971 || year > 3000 ? "" : Integer.toString(year);
    }

    private void setCourseOfAction(LitigationCaseInfo suit, String courseOfActionName, CourseOfAction otherCourseOfAction) {
        CourseOfAction courseOfAction = courseOfActionService.findByNameLike(courseOfActionName);
        if (courseOfAction == null) {
            suit.setCourseOfAction(otherCourseOfAction);
            suit.setOtherStatus(courseOfActionName);
        } else
            suit.setCourseOfAction(courseOfAction);
    }

    private CaseType getCaseTypeByName(String caseTypeName, Map<String, CaseType> caseTypes) {
        CaseType caseType = caseTypes.get(caseTypeName);
        if (caseType == null)
            caseType = caseTypeService.findByNameLike(caseTypeName);
        caseTypes.put(caseTypeName, caseType);
        return caseType;
    }

    private Courts findCourtsByNameLike(String courtName, Long caseTypeId, Map<Long, Map<String, Courts>> caseTypeCourtMap) {
        Map<String, Courts> courts;
        if (caseTypeCourtMap.containsKey(caseTypeId)) {
            courts = caseTypeCourtMap.get(caseTypeId);
        } else {
            courts = new HashMap<>();
            // Get all available courts for particular case type
            courtsService.findByCaseType(caseTypeId).forEach(courtByCaseType -> courts.put(courtByCaseType.getName(), courtByCaseType));
            caseTypeCourtMap.put(caseTypeId, courts);
        }
        Courts court;
        String conciseCourtName = courtName.replaceAll("\\W|\\d", ""); // Search optimization
        if (courts.containsKey(conciseCourtName)) {
            court = courts.get(conciseCourtName);
        } else {
            court = findCourtWithNameConflictFromCache(courts, conciseCourtName);
            courts.put(conciseCourtName, court);
        }
        return court;
    }

    private Courts findCourtWithNameConflictFromCache(Map<String, Courts> courts, String courtName) {
        if (!StringUtils.hasText(courtName)) return null;

        Courts court = null;
        Set<String> courtKeys = courts.keySet();
        double maxSimilarity = 0.51; // Minimum 51% similarity expected
        for (String courtKey : courtKeys) {
            double similarity = stringUtils.findLevenshteinSimilarity(courtKey, courtName);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                court = courts.get(courtKey);
            }
        }
        return court == null ? courts.get("Others") : court;
    }

    private List<Lawyer> findLawyersByNameAndPhoneNumber(String[] lawyerNames, String[] phoneNumbers, String courtJurisdiction) {
        Map<Long, Lawyer> lawyers = new HashMap<>();
        try {
            List<String> names = Arrays.stream(lawyerNames).filter(name -> StringUtils.hasText(name) && name.matches("[A-Za-z \\-.]+")).collect(Collectors.toList());
            List<String> phones = Arrays.stream(phoneNumbers).filter(phone -> StringUtils.hasText(phone) && phone.matches("[0-9\\-]+")).collect(Collectors.toList());
            int length = phones.size();
            if (length == 1) {
//                Lawyer lawyer = new Lawyer();
//                String phone = phones.toString().replaceAll("[\\[\\]]", "");
//                lawyer.setPhoneNo(phone);
//                lawyer.setMobileNo(phone);
//                lawyer.setName(names.get(0));
//                lawyer.setCourtJurisdiction(courtJurisdiction);
                String name = names.isEmpty() ? "----" : names.get(0);
                String phone = phones.isEmpty() ? "----" : phones.get(0);
                Lawyer lawyer = lawyerService.findByContactOrName(phone, name);
                if (lawyer != null && lawyer.getId() != null)
                    lawyers.put(lawyer.getId(), lawyer);
            } else {
                for (int i = 0; i < length; i++) {
//                    Lawyer lawyer = new Lawyer();
//                    String phone = i < phoneNumbers.length ? phoneNumbers[i] : "";
//                    lawyer.setPhoneNo(phone);
//                    lawyer.setMobileNo(phone);
//                    lawyer.setName(lawyerNames[i]);
//                    lawyer.setCourtJurisdiction(courtJurisdiction);
//                    lawyer = lawyerService.findByContactNo(phoneNumbers);
                    String name = names.isEmpty() ? "----" : names.get(0);
                    String phone = phones.isEmpty() ? "----" : phones.get(i);
                    Lawyer lawyer = lawyerService.findByContactOrName(phone, name);
                    if (lawyer != null && lawyer.getId() != null)
                        lawyers.put(lawyer.getId(), lawyer);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(lawyers.values());
    }


    public Date formateDate(Date date){
        Date date1= null;
        if(date != null){
            DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");
            String filingDateString = formate.format(date);
            filingDateString.split(" ,'");
            try {
                date1 =(Date) new SimpleDateFormat("dd-MM-yyyy").parse(filingDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//                filingDateString.replace("/","-");
            System.out.println(filingDateString);

        }
        return date1;
    }

}
