package com.csinfotechbd.legal.dataEntry.caseEntry;


import com.csinfotechbd.legal.setup.lawyers.Lawyer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Tuple;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class LitigationCaseInfoDto {

    private Long id;

    private String caseFiled = "";

    private String caseFiledSubType = "";

    private String caseType = "";


    private String otherCaseType = "";
    private String caseTypeSubType = "";
    private String otherCaseTypeSubType = "";

    private String natureOfWrit = "";
    private String natureOfWritOther = "";

    private String ldNo = "";
    private String zone = "";
    private String branchName = "";
    private String branchCode = "";
    private String nameOfAcc = "";
    private String accusedName = "";
    private String borrowerName = "";
    private String customerCifNo = "";
    private String customerAccNum = "";
    private String clientId = "";
    private String accountNoIf = "";
    private String businessSegment = "";


    private String petitionerName = "";
    private String oppositePartyName = "";
    private String byWhomFiled = "";
    private String subjectMatterOfCase = "";
    private String firstOrderDate;
    private String hearingDate;

    private String typeOfFraud = "";

    private String plaintiffName = "";
    private String plaintiffDesignation = "";
    private String plaintiffPhoneNo = "";
    private String otherDefendantName = "";

    private String defendantName = "";
    private String commentImpactOnBank = "";
    private String landDetails = "";


    private String techniquesTofraud = "";
    private String dateOfOccurrence;
    private String dateOfDetection;
    private Double amountInvolved;
    private Double adjustmentOfDefraudAmount;
    private String nameOfOfficerEmployeeOtherInvolved = "";
    private String actionAgainstDelinquent = "";
    private String currentStatusOfCase = "";
    private String initiativesToPreventIncidentRecurrence = "";
    private String remarks = "";
    private String division = "";

    private Double outstanding;

    private String caseNumber = "";
    private String caseYear = "";
    private String section = "";

    private String court = "";
    private String otherCourt = "";

    private String district = "";

    private String dateOfFilingAR;

    private String dateOfFiling;

    private Double suitValueAr;
    private Double suitValueEx;
    private Double suitValue;
    private Double chequeAmount;


    private String nextDate;
    private String nextCourseOfAction;
    private String previousDate;
    private String previousCourseOfAction;
    private String nextDateFixed = "No";

    private String courseOfAction = "";
    private String courseOfActionContestedType = "";
    private String possessionInFavourOfBank;
    private String registrationDate;
    private String deedNo = "";
    private String otherCourseOfAction = "";

    private String deedDate;

    private String applicationForMutation;

    private String mutationDate;

    private String propertyDisputed = "";

    private String niActCaseNo = "";
    private String artharinSuitNo = "";
    private String writPetitionNum = "";
    private String others = "";
    private String blaAttendance;
    private String writtenOff;

    private Double recoveryBeforeCaseAmount;

    private String recoveryBeforeCaseDate;

    private Double recoveryAfterCaseAmount;

    private String recoveryAfterCaseDate;

    private Double recoveredAmount;

    private String recoveredAmountDate;

    private Double totalLegalExpenseAmount;

    //private String legalExpenseDate;

    private String underSolenama;

    private String lawyer = "";

    private String lawyerCellNum = "";

    private List<String> collateralSecurityL = new ArrayList<>();
    public String collateralSecurity = "";

    private String collSecurityCityCorporation = "";
    private String collSecurityPourasava = "";
    private String outOfCityCorporationPourasava = "";
    private String collSecurityDistrict = "";
    private String collSecurityMouza = "";
    private String collSecuritySro = "";
    private String collSecurityPs = "";
    private String khatianCs = "";
    private String khatianSa = "";
    private String khatianRs = "";
    private String khatianBs = "";
    private String khatianCity = "";
    private String jlCs = "";
    private String jlSa = "";
    private String jlRs = "";
    private String jlBs = "";
    private String jlCity = "";
    private String plotCs = "";
    private String plotSa = "";
    private String plotRs = "";
    private String plotBs = "";
    private String plotCity = "";
    private String boundedByNorth = "";
    private String boundedBySouth = "";
    private String boundedByEast = "";
    private String boundedByWest = "";
    private String otherCollateralSecurity = "";
    private String collateralSecurityFixedAssetDetails = "";

    private Double marketValue;

    private String marketValueDate;

    private Double forcedSaleValue;

    private String forcedSaleValueDate;

    private String assesedBy = "";
    private String surveyorDetail = "";

    private String status = "";
    private String otherStatus = "";
    private String courtJurisdiction;


    public LitigationCaseInfoDto() {

    }

    public LitigationCaseInfoDto(LitigationCaseInfo caseInfo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            caseFiledSubType = caseInfo.getCaseFiledSubType() != null ? caseInfo.getCaseFiledSubType().getName() : "";
            caseType = caseInfo.getCaseType() != null ? caseInfo.getCaseType().getName() : "";
            court = caseInfo.getCourt() != null ? caseInfo.getCourt().getName() : "";
            lawyer = caseInfo.getLawyer().stream().map(Lawyer::getName).collect(Collectors.toList()).toString().replaceAll("[\\[\\]]", "");
            caseFiled = caseInfo.getCaseFiled() != null ? caseInfo.getCaseFiled().getName() : "";
            status = caseInfo.getOtherStatus() != null ? caseInfo.getOtherStatus() : caseInfo.getStatus() != null ? caseInfo.getStatus().getName() : "";

            List<String> collateralSecurityName = new ArrayList<String>();
            if(caseInfo.getCollateralSecurityList().size() != 0)
                collateralSecurityName.add(caseInfo.getCollateralSecurityList().iterator().next().getCollateralSecurityName());
            collateralSecurityL =  collateralSecurityName.size() != 0 ? collateralSecurityName : new ArrayList<String>();
            //collateralSecurity = caseInfo.getCollateralSecurity() != null ? caseInfo.getCollateralSecurity().getName() : "";
            courseOfAction = caseInfo.getCourseOfAction() != null ? caseInfo.getCourseOfAction().getName() : "";

            id = caseInfo.getId();
            otherCaseType = caseInfo.getOtherCaseType();
            caseTypeSubType = caseInfo.getCaseTypeSubType();
            otherCaseTypeSubType = caseInfo.getOtherCaseTypeSubType();
            natureOfWrit = caseInfo.getNatureOfWrit();
            natureOfWritOther = caseInfo.getNatureOfWritOther();
            ldNo = caseInfo.getLdNo();
            zone = caseInfo.getZone();
            branchName = caseInfo.getBranchName();
            branchCode = caseInfo.getBranchCode();
            nameOfAcc = caseInfo.getNameOfAcc();
            accusedName = caseInfo.getAccusedName();
            borrowerName = caseInfo.getBorrowerName();
            customerCifNo = caseInfo.getCustomerCifNo();
            customerAccNum = caseInfo.getCustomerAccNum();
            clientId = caseInfo.getClientId();
            accountNoIf = caseInfo.getAccountNoIf();
            businessSegment = caseInfo.getBusinessSegment();
            petitionerName = caseInfo.getPetitionerName();
            oppositePartyName = caseInfo.getOppositePartyName();
            byWhomFiled = caseInfo.getByWhomFiled();
            subjectMatterOfCase = caseInfo.getSubjectMatterOfCase();
            firstOrderDate = dateFormat.format(caseInfo.getFirstOrderDate());
            hearingDate = dateFormat.format(caseInfo.getHearingDate());
            typeOfFraud = caseInfo.getTypeOfFraud();
            plaintiffName = caseInfo.getPlaintiffName();
            plaintiffDesignation = caseInfo.getPlaintiffDesignation();
            plaintiffPhoneNo = caseInfo.getPlaintiffPhoneNo();
            otherDefendantName = caseInfo.getOtherDefendantName();
            defendantName = caseInfo.getDefendantName();
            commentImpactOnBank = caseInfo.getCommentImpactOnBank();
            landDetails = caseInfo.getLandDetails();
            techniquesTofraud = caseInfo.getTechniquesTofraud();
            dateOfOccurrence = dateFormat.format(caseInfo.getDateOfOccurrence());
            dateOfDetection = dateFormat.format(caseInfo.getDateOfDetection());
            amountInvolved = caseInfo.getAmountInvolved();
            adjustmentOfDefraudAmount = caseInfo.getAdjustmentOfDefraudAmount();
            nameOfOfficerEmployeeOtherInvolved = caseInfo.getNameOfOfficerEmployeeOtherInvolved();
            actionAgainstDelinquent = caseInfo.getActionAgainstDelinquent();
            currentStatusOfCase = caseInfo.getCurrentStatusOfCase();
            initiativesToPreventIncidentRecurrence = caseInfo.getInitiativesToPreventIncidentRecurrence();
            remarks = caseInfo.getRemarks();
            division = caseInfo.getDivision();
            outstanding = caseInfo.getOutstanding();
            caseNumber = caseInfo.getCaseNumber();
            caseYear = caseInfo.getCaseYear();
            section = caseInfo.getSection();
            otherCourt = caseInfo.getOtherCourt();
            district = caseInfo.getDistrict();
            dateOfFilingAR = dateFormat.format(caseInfo.getDateOfFilingAR());
            dateOfFiling = dateFormat.format(caseInfo.getDateOfFiling());
            suitValueAr = caseInfo.getSuitValueAr();
            suitValue = caseInfo.getSuitValue();
            chequeAmount = caseInfo.getChequeAmount();
            nextDate = dateFormat.format(caseInfo.getNextDate());
            nextDateFixed = caseInfo.isNextDateFixed() ? "Yes" : "No";
            courseOfActionContestedType = caseInfo.getCourseOfActionContestedType();
            possessionInFavourOfBank = caseInfo.isPossessionInFavourOfBank() ? "Yes" : "No";
            registrationDate = dateFormat.format(caseInfo.getRegistrationDate());
            deedNo = caseInfo.getDeedNo();
            otherCourseOfAction = caseInfo.getOtherCourseOfAction();
            deedDate = dateFormat.format(caseInfo.getDeedDate());
            applicationForMutation = caseInfo.isApplicationForMutation() ? "Yes" : "No";
            mutationDate = dateFormat.format(caseInfo.getMutationDate());
            propertyDisputed = caseInfo.getPropertyDisputed();
            niActCaseNo = caseInfo.getNiActCaseNo();
            artharinSuitNo = caseInfo.getArtharinSuitNo();
            writPetitionNum = caseInfo.getWritPetitionNum();
            others = caseInfo.getOthers();
            blaAttendance = caseInfo.isBlaAttendance() ? "Yes" : "No";
            writtenOff = caseInfo.isWrittenOff() ? "Yes" : "No";
            recoveryBeforeCaseAmount = caseInfo.getRecoveryBeforeCaseAmount();
            recoveryBeforeCaseDate = dateFormat.format(caseInfo.getRecoveryBeforeCaseDate());
            recoveryAfterCaseAmount = caseInfo.getRecoveryAfterCaseAmount();
            recoveryAfterCaseDate = dateFormat.format(caseInfo.getRecoveryAfterCaseDate());
            recoveredAmount = caseInfo.getRecoveredAmount();
            recoveredAmountDate = dateFormat.format(caseInfo.getRecoveredAmountDate());
            totalLegalExpenseAmount = caseInfo.getTotalLegalExpenseAmount();
            /* legalExpenseDate = dateFormat.format(caseInfo.getLegalExpenseDate());*/
            underSolenama = caseInfo.isUnderSolenama() ? "Yes" : "No";
            lawyerCellNum = caseInfo.getLawyerCellNum();
/*            collSecurityCityCorporation = caseInfo.getCollSecurityCityCorporation();
            collSecurityPourasava = caseInfo.getCollSecurityPourasava();
            outOfCityCorporationPourasava = caseInfo.getOutOfCityCorporationPourasava();
            collSecurityDistrict = caseInfo.getCollSecurityDistrict();
            collSecurityMouza = caseInfo.getCollSecurityMouza();
            collSecuritySro = caseInfo.getCollSecuritySro();
            collSecurityPs = caseInfo.getCollSecurityPs();
            khatianCs = caseInfo.getKhatianCs();
            khatianSa = caseInfo.getKhatianSa();
            khatianRs = caseInfo.getKhatianRs();
            khatianBs = caseInfo.getKhatianBs();
            khatianCity = caseInfo.getKhatianCity();
            jlCs = caseInfo.getJlCs();
            jlSa = caseInfo.getJlSa();
            jlRs = caseInfo.getJlRs();
            jlBs = caseInfo.getJlBs();
            jlCity = caseInfo.getJlCity();
            plotCs = caseInfo.getPlotCs();
            plotSa = caseInfo.getPlotSa();
            plotRs = caseInfo.getPlotRs();
            plotBs = caseInfo.getPlotBs();
            plotCity = caseInfo.getPlotCity();
            boundedByNorth = caseInfo.getBoundedByNorth();
            boundedBySouth = caseInfo.getBoundedBySouth();
            boundedByEast = caseInfo.getBoundedByEast();
            boundedByWest = caseInfo.getBoundedByWest();
            otherCollateralSecurity = caseInfo.getOtherCollateralSecurity();
            collateralSecurityFixedAssetDetails = caseInfo.getCollateralSecurityFixedAssetDetails();*/
            marketValue = caseInfo.getMarketValue();
            marketValueDate = dateFormat.format(caseInfo.getMarketValueDate());
            forcedSaleValue = caseInfo.getForcedSaleValue();
            forcedSaleValueDate = dateFormat.format(caseInfo.getForcedSaleValueDate());
            assesedBy = caseInfo.getAssesedBy();
            surveyorDetail = caseInfo.getSurveyorDetail();
            otherStatus = caseInfo.getOtherStatus();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public LitigationCaseInfoDto(Tuple data){
        id = ((Number) Optional.ofNullable(data.get("ID")).orElse(0)).longValue();
        caseFiled = Objects.toString(data.get("CASE_FILED"), "-");
        caseFiledSubType = Objects.toString(data.get("CASE_FILED_SUB_TYPE"), "-");
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        caseTypeSubType = Objects.toString(data.get("CASE_TYPE_SUB_TYPE"), "-");
        division = Objects.toString(data.get("DIVISION"), "-");
        district = Objects.toString(data.get("DISTRICT"), "-");
        zone = Objects.toString(data.get("ZONE"), "-");
        courtJurisdiction = Objects.toString(data.get("COURT_JURISDICTION"), "-");
        ldNo = Objects.toString(data.get("LD_NO"), "-");
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        nameOfAcc = Objects.toString(data.get("NAME_OF_ACC"), "-");
        borrowerName = Objects.toString(data.get("BORROWER_NAME"), "-");
        accusedName = Objects.toString(data.get("ACCUSED_NAME"), "-");
        businessSegment = Objects.toString(data.get("BUSINESS_SEGMENT"), "-");
        customerCifNo = Objects.toString(data.get("CUSTOMER_CIF_NO"), "-");
        clientId = Objects.toString(data.get("CLIENT_ID"), "-");
        customerAccNum = Objects.toString(data.get("CUSTOMER_ACC_NUM"), "-");
        plaintiffName = Objects.toString(data.get("PLAINTIFF_NAME"), "-");
        plaintiffDesignation = Objects.toString(data.get("PLAINTIFF_DESIGNATION"), "-");
        plaintiffPhoneNo = Objects.toString(data.get("PLAINTIFF_PHONE_NO"), "-");
        defendantName = Objects.toString(data.get("DEFENDANT_NAME"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        dateOfFilingAR = Objects.toString(data.get("DATE_OF_FILING_EX"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        suitValueEx = ((Number) Optional.ofNullable(data.get("SUIT_VALUE_EX")).orElse(0)).doubleValue();
        previousDate = Objects.toString(data.get("PREVIOUS_DATE"), "-");
        previousCourseOfAction = Objects.toString(data.get("PREVIOUS_COURSE_OF_ACTION"), "-");
        nextDate = Objects.toString(data.get("NEXT_DATE"), "-");
        nextCourseOfAction = Objects.toString(data.get("COURSE_OF_ACTION_NAME"), "-");//Objects.toString(data.get("NEXT_COURSE_OF_ACTION"), "-");
        court = Objects.toString(data.get("COURT"), "-");
        writtenOff = Objects.toString(data.get("WRITTEN_OFF"), "-");
        totalLegalExpenseAmount = ((Number) Optional.ofNullable(data.get("TOTAL_LEGAL_EXPENSE_AMOUNT")).orElse(0)).doubleValue();
        lawyer = Objects.toString(data.get("LAWYER"), "-");
        lawyerCellNum = Objects.toString(data.get("LAWYER_CELL_NUMBER"), "-");
        //collateralSecurityL = (List<String>)data.get("COLLATERAL_SECURITY");
        collateralSecurityL.add(Objects.toString((data.get("COLLATERAL_SECURITY")), "-"));
        collateralSecurity = Objects.toString((data.get("COLLATERAL_SECURITY")), "-");
        marketValue = ((Number) Optional.ofNullable(data.get("MARKET_VALUE")).orElse(0)).doubleValue();
        forcedSaleValue = ((Number) Optional.ofNullable(data.get("FORCED_SALE_VALUE")).orElse(0)).doubleValue();
        assesedBy = Objects.toString(data.get("ASSESED_BY"), "-");
        underSolenama = Objects.toString(data.get("UNDER_SOLENAMA"), "-");
        recoveryBeforeCaseAmount = ((Number) Optional.ofNullable(data.get("RECOVERY_BEFORE_CASE_AMOUNT")).orElse(0)).doubleValue();
        recoveryAfterCaseAmount = ((Number) Optional.ofNullable(data.get("RECOVERY_AFTER_CASE_AMOUNT")).orElse(0)).doubleValue();
        typeOfFraud = Objects.toString(data.get("TYPE_OF_FRAUD"), "-");
        techniquesTofraud = Objects.toString(data.get("TECHNIQUES_TOFRAUD"), "-");
        dateOfOccurrence = Objects.toString(data.get("DATE_OF_OCCURRENCE"), "-");
        dateOfDetection = Objects.toString(data.get("DATE_OF_DETECTION"), "-");
        adjustmentOfDefraudAmount = ((Number) Optional.ofNullable(data.get("ADJUSTMENT_OF_DEFRAUD_AMOUNT")).orElse(0)).doubleValue();
        nameOfOfficerEmployeeOtherInvolved = Objects.toString(data.get("NAME_OF_OFFICER_EMPLOYEE_OTHER_INVOLVED"), "-");
        actionAgainstDelinquent = Objects.toString(data.get("ACTION_AGAINST_DELINQUENT"), "-");
        initiativesToPreventIncidentRecurrence = Objects.toString(data.get("INITIATIVES_TO_PREVENT_INCIDENT_RECURRENCE"), "-");
        section = Objects.toString(data.get("SECTION"), "-");
        commentImpactOnBank = Objects.toString(data.get("COMMENT_IMPACT_ON_BANK"), "-");
        subjectMatterOfCase = Objects.toString(data.get("SUBJECT_MATTER_OF_CASE"), "-");
        firstOrderDate = Objects.toString(data.get("FIRST_ORDER_DATE"), "-");
        status = Objects.toString(data.get("STATUS"), "-");
    }


}
