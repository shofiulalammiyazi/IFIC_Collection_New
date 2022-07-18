package com.csinfotechbd.legal.dataEntry.caseEntry;

import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.collection.settings.district.DistrictDto;
import com.csinfotechbd.collection.settings.district.DistrictEntity;
import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.collection.settings.division.DivisionEntity;
import com.csinfotechbd.collection.settings.division.DivisionService;
import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubType;
import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubTypeService;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeDto;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import com.csinfotechbd.legal.setup.caseStatus.CaseStatus;
import com.csinfotechbd.legal.setup.caseStatus.CaseStatusService;
import com.csinfotechbd.legal.setup.caseType.CaseTypeDto;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.legal.setup.collateralSecurity.CollateralSecurity;
import com.csinfotechbd.legal.setup.collateralSecurity.CollateralSecurityService;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfAction;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfActionDto;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfActionService;
import com.csinfotechbd.legal.setup.courts.Courts;
import com.csinfotechbd.legal.setup.courts.CourtsDto;
import com.csinfotechbd.legal.setup.courts.CourtsService;
import com.csinfotechbd.legal.setup.lawyers.Lawyer;
import com.csinfotechbd.legal.setup.lawyers.LawyerDto;
import com.csinfotechbd.legal.setup.lawyers.LawyerService;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneDto;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneEntity;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionService;
import javassist.bytecode.stackmap.BasicBlock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/legal/setup/rest")
public class LitigationRestController {

    private final CaseFiledTypeService caseFiledTypeService;
    private final CaseFiledSubTypeService caseFiledSubTypeService;
    private final CaseTypeService caseTypeService;
    private final CourtsService courtsService;
    private final BranchService branchService;
    private final DistrictService districtService;
    private final DivisionService divisionService;
    private final LawyerService lawyerService;
    private final CaseStatusService caseStatusService;
    private final CourseOfActionService courseOfActionService;
    private final CollateralSecurityService collateralSecurityService;
    private final LitigationCaseInfoService litigationCaseInfoService;
    private final LitigationRestService litigationRestService;
    private final LitigationZoneService litigationZoneService;
    private final CardAccountBasicService cardAccountBasicService;

    @GetMapping("/case-filed")
    public List<CaseFiledTypeDto> getCaseFilesType() {
        List<CaseFiledType> caseFiledTypes = caseFiledTypeService.findByEnabled(true);
        List<CaseFiledTypeDto> caseFiledTypeList = caseFiledTypes.stream()
                .map(CaseFiledTypeDto::new).collect(Collectors.toList());

        return caseFiledTypeList;
    }

    @GetMapping("/case-filed-sub-type")
    public List<CaseFiledSubType> getCaseFiledSubType() {
        return caseFiledSubTypeService.findByEnabled(true);
    }

    @GetMapping("/case-suit")
    public List<CaseTypeDto> getCaseSuitType(@RequestParam("csFiledType") Long caseFiledTypeId, @RequestParam(name = "csFiledSubType", required = false) Long caseFiledSubType) {
        List<CaseTypeDto> caseTypeList;
        if (caseFiledSubType == null)
            caseTypeList = caseTypeService.findByCaseFiledType(caseFiledTypeId).stream()
                    .map(CaseTypeDto::new).collect(Collectors.toList());
        else
            caseTypeList = caseTypeService.findByCaseFiledTypeAndCaseFiledSubType(caseFiledTypeId, caseFiledSubType)
                    .stream().map(CaseTypeDto::new).collect(Collectors.toList());
        return caseTypeList;
    }

    @GetMapping("/case-suit/enabled")
    public List<CaseTypeDto> getEnabledCaseSuitType(@RequestParam("csFiledType") Long caseFiledTypeId, @RequestParam(name = "csFiledSubType", required = false) Long caseFiledSubType) {
        List<CaseTypeDto> caseTypeList;
        if (caseFiledSubType == null)
            caseTypeList = caseTypeService.findByCaseFiledType(caseFiledTypeId, true).stream()
                    .map(CaseTypeDto::new).collect(Collectors.toList());
        else
            caseTypeList = caseTypeService.findByCaseFiledTypeAndCaseFiledSubType(caseFiledTypeId, caseFiledSubType, true)
                    .stream().map(CaseTypeDto::new).collect(Collectors.toList());
        return caseTypeList;
    }

    @GetMapping("/courts")
    public List<CourtsDto> getCourtsNames(Long caseTypeId) {
        List<Courts> courts = courtsService.findByCaseType(caseTypeId);
        List<CourtsDto> courtList = courts.stream()
                .map(CourtsDto::new).collect(Collectors.toList());
        return courtList;
    }

    @GetMapping("/zones")
    public List<LitigationZoneEntity> getZoneNames() {
//        List<LitigationZoneDto> litigationZone = litigationZoneService.findActiveListWithBranchNames();
//        List<String> zoneList = litigationZone.stream()
//                .map(LitigationZoneEntity::getName).collect(Collectors.toList());
//        return zoneList;
        return litigationZoneService.findByEnabled(true);
    }

//    @GetMapping("/districts")
//    public List<String> getDistrictName() {
//        List<DistrictEntity> districts = districtService.getActiveOnly();
//        List<DistrictDto> districtList = districts.stream()
//                .map(DistrictDto::new).collect(Collectors.toList());
//        return districtList;
//    }

    @GetMapping("/districts")
    public List<DistrictDto> getDistrictName() {
        List<DistrictEntity> districts = districtService.getActiveOnly();
        List<DistrictDto> districtList = districts.stream()
                .map(DistrictDto::new).collect(Collectors.toList());
        return districtList;
    }

    @GetMapping("/branches")
    public List<Branch> getBranchName() {
        List<Branch> branches = branchService.getActiveList();
//        List<String> branchList = branches.stream()
//                .map(Branch::getBranchName).collect(Collectors.toList());
//        return branchList;
        return branches;
    }
//
//    @GetMapping("/branches")
//    public List<BranchDto> getBranchName() {
//        List<Branch> branches = branchService.getActiveList();
//        List<BranchDto> branchList = branches.stream()
//                .map(BranchDto::new).collect(Collectors.toList());
//        return branchList;
//    }

    @GetMapping("/divisions")
    public List<String> getDivisionName() {
        List<DivisionEntity> divisions = divisionService.getActiveList();
        List<String> divisionList = divisions.stream()
                .map(DivisionEntity::getDivName).collect(Collectors.toList());
        return divisionList;
    }

    @GetMapping("/lawyers")
    public List<LawyerDto> getLawyers() {
        List<Lawyer> lawyers = lawyerService.findByEnabled(true);
        List<LawyerDto> districtList = lawyers.stream()
                .map(LawyerDto::new).collect(Collectors.toList());
        return districtList;
    }

    @GetMapping("/case-status")
    public List<CaseStatus> getCaseStatus(Long caseTypeId) {
        return caseStatusService.findByCaseType(caseTypeId);
    }

    @GetMapping("/collateral-security")
    public List<CollateralSecurity> getCollateralSecurities(Long caseTypeId) {
        return collateralSecurityService.findByCaseType(caseTypeId);
    }

    @GetMapping("/course-of-action")
    public List<CourseOfActionDto> getCourseOfActionList(@RequestParam("caseTypeId") Long caseTypeId) {
        return courseOfActionService.findByCaseTypeId(caseTypeId)
                .stream().map(CourseOfActionDto::new).collect(Collectors.toList());
    }

    @GetMapping("/course-of-action-by-contested")
    public List<CourseOfActionDto> courseOfActionByContested(Long caseTypeId, String contestedType) {
        return courseOfActionService.findByCaseTypeIdAndContestedType(caseTypeId, contestedType);
    }

    @GetMapping("/course-of-action-sub-property")
    public CourseOfAction courseOfActionSubProperty(@RequestParam("courseOfActionId") Long courseOfActionId) {
        return courseOfActionService.findById(courseOfActionId);
    }

    @PostMapping(value = "/save-litigation")
    public ResponseEntity<String> saveLitigation(@RequestBody LitigationCaseInfo litigationInfo) {
        try {
            litigationCaseInfoService.save(litigationInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Something went wrong");
        }
        return ResponseEntity.ok("succeed");
    }

    @GetMapping("/update-litigation/{id}")
    public LitigationCaseInfo updateLitigationCaseInfo(@PathVariable("id") Long id) {
        LitigationCaseInfo litigationInfo = litigationCaseInfoService.getLitigationCaseInfo(id);
        litigationInfo.setBusinessSegment("Business");
        litigationCaseInfoService.save(litigationInfo);
        return litigationInfo;
    }

    @GetMapping("/get-litigations")
    public List<LitigationCaseInfo> getAllLitigationInfoList() {
//        List<LitigationCaseInfo> caseList = litigationCaseInfoService.findAllLitigationCaseInfo().stream()
//                .sorted(Comparator.comparing(LitigationCaseInfo::getCreatedBy, Comparator.nullsFirst(Comparator.naturalOrder())))
//                .collect(Collectors.toList());
        List<LitigationCaseInfo> caseList = litigationCaseInfoService.findAllLitigationCaseInfo();

        return caseList;
    }

    @GetMapping("/get-litigations/{cof}")
    public List<LitigationCaseInfo> getAllLitigationInfoList(@PathVariable String cof) {
        Long courseOfActionId;
        try {
            courseOfActionId = Long.parseLong(cof);
        } catch (Exception ex) {
            return litigationCaseInfoService.findAllLitigationCaseInfo();
        }
        System.out.println(courseOfActionId);
        return litigationCaseInfoService.getByCourseOfAction(courseOfActionId);
    }

    @GetMapping("/get-litigations-by-nextdate")
    public List<LitigationCaseInfo> getAllLitigationInfoListByDate(@RequestParam("fdate") String fDate, @RequestParam("tdate") String tDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = sdf.parse(fDate);
            toDate = sdf.parse(tDate);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return litigationCaseInfoService.getBetweenNextDate(fromDate, toDate);
    }

    @GetMapping("/get-litigation-revision")
    public List<LitigationCaseInfo> getLitigationRevision(@RequestParam("id") Long id) {
        List<LitigationCaseInfo> litigationCaseInfos = litigationCaseInfoService.getLitigationRevisions(id);
//        litigationCaseInfos.remove(0);
        return litigationCaseInfos;
    }

    @GetMapping("/account-info")
    public LitigationCaseInfoDto getAccountInfo(@RequestParam("accountNo") String accountNo) {
       return litigationRestService.getAccountInfoFromApi(accountNo.split(","));
    }

    @GetMapping("/get-lawyer-change-history")
    public List<String> getLawyerChangeHistory(@RequestParam("id") Long id) {
        return litigationCaseInfoService.getLawyerChangeHistory(id);
    }
//
//    @GetMapping("/get-lawyer-change-history")
//    public List<LawyerDto> getLawyerChangeHistory(@RequestParam("id") Long id) {
//        List<Long> lawysersIdList = litigationCaseInfoService.getLawyerChangeHistory(id);
//        List<Lawyer> lawyerList = lawyerService.findById(lawysersIdList);
//        return lawyerList.stream().map(LawyerDto::new).collect(Collectors.toList());
//    }

    @GetMapping("/get-plaintiff-change-history")
    public List<String> getPlaintiffChangeHistory(@RequestParam("id") Long id) {
        return litigationCaseInfoService.getPlaintiffChangeHistory(id);
    }

    @GetMapping("/get-plaintiff-change-history-date")
    public List<String> getPlaintiffChangeHistoryDate(@RequestParam("id") Long id) {
        return litigationCaseInfoService.getPlaintiffChangeHistoryDate(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @GetMapping("/card-data")
    public CardInfoDto getData(@RequestParam String clientId){
        try{
            CardInfoDto cardInfoDto = cardAccountBasicService.getCardInfoForLegal(clientId);
            return cardInfoDto;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
