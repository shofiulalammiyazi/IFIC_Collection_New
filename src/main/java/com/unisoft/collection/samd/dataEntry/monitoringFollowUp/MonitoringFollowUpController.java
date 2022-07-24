package com.unisoft.collection.samd.dataEntry.monitoringFollowUp;


import com.unisoft.collection.samd.setup.bBAuditType.BBAuditType;
import com.unisoft.collection.samd.setup.bBAuditType.BBAuditTypeService;
import com.unisoft.collection.samd.setup.bbcommentsforclassification.BbCommentsForClassification;
import com.unisoft.collection.samd.setup.bbcommentsforclassification.BbCommentsForClassificationService;
import com.unisoft.collection.samd.setup.borrowerGuarantorsCapabilityToRepay.BorrowerGuarantorCapabilityRepay;
import com.unisoft.collection.samd.setup.borrowerGuarantorsCapabilityToRepay.BorrowerGuarantorCapabilityRepayService;
import com.unisoft.collection.samd.setup.borrowerPresentStatus.BorrowerPresentStatus;
import com.unisoft.collection.samd.setup.borrowerPresentStatus.BorrowerPresentStatusService;
import com.unisoft.collection.samd.setup.borrowerbusinessstatus.BorrowerBusinessStatus;
import com.unisoft.collection.samd.setup.borrowerbusinessstatus.BorrowerBusinessStatusService;
import com.unisoft.collection.samd.setup.borrowerguarantoravailability.BorrowerAndGuarantorAvailability;
import com.unisoft.collection.samd.setup.borrowerguarantoravailability.BorrowerAndGuarantorAvailabilityService;
import com.unisoft.collection.samd.setup.borrowerstayingat.BorrowerStaying;
import com.unisoft.collection.samd.setup.borrowerstayingat.BorrowerStayingService;
import com.unisoft.collection.samd.setup.capableInfluenceCustomerBankDue.CapableInfluenceCustomerBankDue;
import com.unisoft.collection.samd.setup.capableInfluenceCustomerBankDue.CapableInfluenceCustomerBankDueService;
import com.unisoft.collection.samd.setup.categoryForDelinquency.CategoryDelinquency;
import com.unisoft.collection.samd.setup.categoryForDelinquency.CategoryForDelinquencyService;
import com.unisoft.collection.samd.setup.interestedFurtherLoan.InterestedFurtherLoan;
import com.unisoft.collection.samd.setup.interestedFurtherLoan.InterestedFurtherLoanService;
import com.unisoft.collection.samd.setup.loanLiabilityRecoverability.LoanLiabilityRecoverability;
import com.unisoft.collection.samd.setup.loanLiabilityRecoverability.LoanLiabilityRecoverabilityService;
import com.unisoft.collection.samd.setup.logicInTerms.LogicInTerms;
import com.unisoft.collection.samd.setup.logicInTerms.LogicInTermsService;
import com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified.PossibilityProbability;
import com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified.PossibilityProbabilityService;
import com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified.PossibilityProbabilityClassified;
import com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified.PossibilityProbabilityClassifiedService;
import com.unisoft.collection.samd.setup.sourceOfRecovery.SourceOfRecovery;
import com.unisoft.collection.samd.setup.sourceOfRecovery.SourceOfRecoveryService;
import com.unisoft.collection.samd.setup.sourceofrecoverytools.SourceOfRecoveryTools;
import com.unisoft.collection.samd.setup.sourceofrecoverytools.SourceOfRecoveryToolsService;
import com.unisoft.collection.samd.setup.wayForwardReduceFromNPL.WayForwardReduceFromNPL;
import com.unisoft.collection.samd.setup.wayForwardReduceFromNPL.WayForwardReduceFromNPLService;
import com.unisoft.collection.samd.setup.whethertheloanistetd.WhetherTheLoanTeTdRcRs;
import com.unisoft.collection.samd.setup.whethertheloanistetd.WhetherTheLoanTeTdRcRsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/samd/dataEntry/monitoring-follow-up")
public class MonitoringFollowUpController {


    @Autowired
    private MonitoringFollowUpService monitoringFollowUpService;
    @Autowired
    private WhetherTheLoanTeTdRcRsService whetherTheLoanTeTdRcRsService;

    @Autowired
    private SourceOfRecoveryToolsService sourceOfRecoveryToolsService;
    @Autowired
    private BorrowerStayingService borrowerStayingService;

    @Autowired
    private BorrowerBusinessStatusService borrowerBusinessStatusService;
    @Autowired
    private InterestedFurtherLoanService interestedFurtherLoanService;
    @Autowired
    private SourceOfRecoveryService sourceOfRecoveryService;
    @Autowired
    private BorrowerPresentStatusService borrowerPresentStatusService;
    @Autowired
    private LogicInTermsService logicInTermsService;
    @Autowired
    private BorrowerAndGuarantorAvailabilityService borrowerAndGuarantorAvailabilityService;
    @Autowired
    private PossibilityProbabilityService possibilityProbabilityService;
    @Autowired
    private BorrowerGuarantorCapabilityRepayService borrowerGuarantorCapabilityRepayService;
    @Autowired
    private PossibilityProbabilityClassifiedService possibilityProbabilityClassifiedService;
    @Autowired
    private WayForwardReduceFromNPLService wayForwardReduceFromNPLService;
    @Autowired
    private LoanLiabilityRecoverabilityService loanLiabilityRecoverabilityService;
    @Autowired
    private CategoryForDelinquencyService categoryForDelinquencyService;
    @Autowired
    private CapableInfluenceCustomerBankDueService capableInfluenceCustomerBankDueService;
    @Autowired
    private BBAuditTypeService bbAuditTypeService;
    @Autowired
    private BbCommentsForClassificationService bbCommentsForClassificationService;


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public MonitoringAndFollowUp createMonitoringFollowUp(MonitoringAndFollowUp monitoringFollowUp, @RequestPart MultipartFile file){
        MonitoringAndFollowUp monitoringFollowUp1 = monitoringFollowUpService.saveMonitoringFollowUp(monitoringFollowUp, file);
        return monitoringFollowUp1;
    }


    @GetMapping("/list")
    @ResponseBody
    public List<MonitoringAndFollowUpDto> getMonitoringFollowUpByCustomerId(@RequestParam String customerId){
        List<MonitoringAndFollowUpDto> monitoringFollowUpList = monitoringFollowUpService.findMonitoringFollowUpByCustomerId(customerId);
        return monitoringFollowUpList;
    }


    @GetMapping("/find")
    @ResponseBody
    public MonitoringAndFollowUp findMonitoringFollowUpById(@RequestParam Long id){
        MonitoringAndFollowUp monitoringFollowUp = monitoringFollowUpService.findMonitoringFollowUpById(id);
        return monitoringFollowUp;
    }


    @GetMapping("/all-value")
    @ResponseBody
    public Map<String, Object> getAllValueForSelect(){
        Map<String, Object> objectMap = new HashMap<>();
        List<WhetherTheLoanTeTdRcRs> whetherTheLoanTeTdRcRs = whetherTheLoanTeTdRcRsService.whetherTheLoanTeTdRcRsList();
        objectMap.put("whetherTheLoanTeTdRcRs", whetherTheLoanTeTdRcRs);
        List<SourceOfRecoveryTools> sourceOfRecoveryTools = sourceOfRecoveryToolsService.findSourceOfRecoveryTools();
        objectMap.put("sourceOfRecoveryTools", sourceOfRecoveryTools);
        List<BorrowerStaying> borrowerStayingList = borrowerStayingService.findBorrowerStaingAll();
        objectMap.put("borrowerStayingList", borrowerStayingList);
        List<BorrowerBusinessStatus> borrowerBusinessStatusList = borrowerBusinessStatusService.findAllBorrowerBusinessStatus();
        objectMap.put("borrowerBusinessStatusList", borrowerBusinessStatusList);
        List<InterestedFurtherLoan> interestedFurtherLoanList = interestedFurtherLoanService.findAllActive();
        objectMap.put("interestedFurtherLoanList", interestedFurtherLoanList);
        List<SourceOfRecovery> sourceOfRecoveryList = sourceOfRecoveryService.findAll();
        objectMap.put("sourceOfRecoveryList", sourceOfRecoveryList);
        List<BorrowerPresentStatus> borrowerPresentStatusList = borrowerPresentStatusService.findAll();
        objectMap.put("borrowerPresentStatusList", borrowerPresentStatusList);
        List<LogicInTerms> logicInTermsList = logicInTermsService.findAll();
        objectMap.put("logicInTermsList", logicInTermsList);
        List<BorrowerAndGuarantorAvailability> borrowerAndGuarantorAvailabilityList = borrowerAndGuarantorAvailabilityService.findAllBorrowerAndGuarantorAvailability();
        objectMap.put("borrowerAndGuarantorAvailabilityList", borrowerAndGuarantorAvailabilityList);
        List<PossibilityProbability> possibilityProbabilityUnclassifiedList = possibilityProbabilityService.objectList();
        objectMap.put("possibilityProbabilityUnclassifiedList", possibilityProbabilityUnclassifiedList);
        List<BorrowerGuarantorCapabilityRepay> borrowerGuarantorCapabilityRepayList = borrowerGuarantorCapabilityRepayService.objectList();
        objectMap.put("borrowerGuarantorCapabilityRepayList", borrowerGuarantorCapabilityRepayList);
        List<PossibilityProbabilityClassified> possibilityProbabilityClassifiedList = possibilityProbabilityClassifiedService.objectList();
        objectMap.put("possibilityProbabilityClassifiedList", possibilityProbabilityClassifiedList);
        List<WayForwardReduceFromNPL> wayForwardReduceFromNPLList = wayForwardReduceFromNPLService.findAll();
        objectMap.put("wayForwardReduceFromNPLList", wayForwardReduceFromNPLList);
        List<LoanLiabilityRecoverability> loanLiabilityRecoverabilityList = loanLiabilityRecoverabilityService.findAll();
        objectMap.put("loanLiabilityRecoverabilityList", loanLiabilityRecoverabilityList);
        List<CategoryDelinquency> categoryForDelinquencyList = categoryForDelinquencyService.findAll();
        objectMap.put("categoryForDelinquencyList", categoryForDelinquencyList);
        List<CapableInfluenceCustomerBankDue>capableInfluenceCustomerBankDueList = capableInfluenceCustomerBankDueService.findAll();
        objectMap.put("capableInfluenceCustomerBankDueList", capableInfluenceCustomerBankDueList);
        List<BBAuditType> bbAuditTypeList = bbAuditTypeService.findAll();
        objectMap.put("bbAuditTypeList", bbAuditTypeList);
        List<BbCommentsForClassification> bbCommentsForClassificationList = bbCommentsForClassificationService.objectList();
        objectMap.put("bbCommentsForClassificationList", bbCommentsForClassificationList);
        return objectMap;
    }


}
