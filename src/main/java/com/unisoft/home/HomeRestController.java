package com.unisoft.home;

import com.unisoft.collection.accountescalation.AccountEscalation;
import com.unisoft.collection.accountescalation.AccountEscalationService;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.customerComplain.CustomerComplainService;
import com.unisoft.collection.customerComplain.CustomerComplainViewModel;
import com.unisoft.collection.dashboard.DashboardService;
import com.unisoft.collection.settings.customerRequest.CustomerRequestsEntity;
import com.unisoft.collection.settings.customerRequest.CustomerRequestsService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.visitLedger.VisitLedgerEntity;
import com.unisoft.collection.settings.visitLedger.VisitLedgerService;
import com.unisoft.customerloanprofile.AdditionalInfo.AdditionalInfo;
import com.unisoft.customerloanprofile.AdditionalInfo.AdditionalInfoService;
import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoService;
import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoService;
import com.unisoft.loanApi.model.CustomerAndBorrowerInfo;
import com.unisoft.loanApi.service.CustomerOtherInfoService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home/rest/")
public class HomeRestController {

    @Autowired
    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ReferenceInfoService referenceInfoService;

    @Autowired
    private GuarantorInfoService guarantorInfoService;

    @Autowired
    private CustomerOtherInfoService customerOtherInfoService;

    @Autowired
    private CustomerComplainService customerComplainService;

    @Autowired
    private CustomerRequestsService customerRequestsService;

    @Autowired
    private AdditionalInfoService additionalInfoService;

    @Autowired
    private VisitLedgerService visitLedgerService;

    @Autowired
    private AccountEscalationService escalationService;

    @GetMapping(value = "notification")
    public String getNotification(){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> pinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(principal.getUsername());
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());
        pinList.add(employeeInfoEntity.getPin());
        List<PeopleAllocationLogicInfo> allocationLogicList =  dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(),
                employeeInfoEntity.getDesignation().getName(), "Loan");

        List<ReferenceInfoEntity> referenceInfoEntities = new ArrayList<>();
        List<GuarantorInfoEntity> guarantorInfoEntities = new ArrayList<>();
        List<CustomerAndBorrowerInfo> customerAndBorrowerInfos = new ArrayList<>();
        List<CustomerRequestsEntity> customerRequestsEntities = new ArrayList<>();
        List<AdditionalInfo> additionalInfos = new ArrayList<>();
        List<String> tlPinList = new ArrayList<>();


        for (PeopleAllocationLogicInfo peopleAllocationLogicInfo : allocationLogicList) {
            List<ReferenceInfoEntity> referenceInfoEntityList =
                    referenceInfoService.findReferenceInfoEntityByDealerPinBndStatus(
                            peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

            List<GuarantorInfoEntity> guarantorInfoEntityList =
                    guarantorInfoService.findGuarantorInfoEntityByDealerPinBndStatus(
                            peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

            List<CustomerAndBorrowerInfo> customerAndBorrowerInfoList =
                    customerOtherInfoService.findCustomerAndBorrowerInfoByDealerPinBndStatus(
                            peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

            List<CustomerRequestsEntity> customerRequestsEntityList =
                    customerRequestsService.findCustomerRequestsEntityByDealerPinBndStatus(
                            peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

            List<AdditionalInfo> additionalInfoList =
                    additionalInfoService.findAdditionalInfoByDealerPinBndStatus(
                            peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

            referenceInfoEntities.addAll(referenceInfoEntityList);

            guarantorInfoEntities.addAll(guarantorInfoEntityList);

            customerAndBorrowerInfos.addAll(customerAndBorrowerInfoList);

            //customerComplainEntities.addAll(customerComplainEntityList);

            customerRequestsEntities.addAll(customerRequestsEntityList);

            additionalInfos.addAll(additionalInfoList);

            tlPinList.add(peopleAllocationLogicInfo.getDealer().getPin());
        }

        List<CustomerComplainViewModel> customerComplainEntityList =
                customerComplainService.getCustomerComplainByDealerPinList(tlPinList);

        List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();
        for (PeopleAllocationLogicInfo logicInfo: allocationLogicList){
            System.out.println(logicInfo.getDealer().getPin());
            List<VisitLedgerEntity> visitLedgerEntities = visitLedgerService.findVisitLedgerEntityByCreatedByAndStatus(logicInfo.getDealer().getPin());
            if (!visitLedgerEntities.isEmpty()){
                visitLedgerEntityList.addAll(visitLedgerEntities);
            }
        }

        List<String> pinList1 = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(employeeInfoEntity.getPin());
        pinList.add(employeeInfoEntity.getPin());
        List<AccountEscalation> escalationList = escalationService.findByToUserPinAndStatus(pinList);




        return String.valueOf((referenceInfoEntities.size() + guarantorInfoEntities.size()
                + customerAndBorrowerInfos.size() + customerComplainEntityList.size() + customerRequestsEntities.size() + visitLedgerEntityList.size() + escalationList.size()+additionalInfos.size()));
    }
}
