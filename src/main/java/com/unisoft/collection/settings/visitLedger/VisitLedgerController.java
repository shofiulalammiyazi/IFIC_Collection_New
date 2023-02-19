package com.unisoft.collection.settings.visitLedger;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.distribution.card.CardRepository;
import com.unisoft.collection.settings.assetMainClassificationLoan.LoanMainClassificationDto;
import com.unisoft.collection.settings.district.DistrictEntity;
import com.unisoft.collection.settings.district.DistrictRepository;
import com.unisoft.collection.settings.district.DistrictService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.loanApi.model.CustomerInfo;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.collection.distribution.loan.LoanRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/visitledger/")
public class VisitLedgerController {

    @Autowired
    private VisitLedgerService visitLedgerService;
    @Autowired
    private VisitLedgerRepository visitLedgerRepository;
    @Autowired
    private LoanAccountBasicService loanAccountBasicService;
    @Autowired
    private CardAccountBasicService cardAccountBasicService;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private RetailLoanUcbApiService dbService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private DistrictRepository districtRepository;


    @GetMapping("customer-info")
    public List<String> getCustomerAddresses(String customerNo) {
        List<String> customerAddresses = new ArrayList<>();
        CustomerInfo customerInfo = dbService.getCustomerInfo(customerNo);
        customerAddresses.add(customerInfo.getPermanentAddress());
        customerAddresses.add(customerInfo.getCorrespondenceAddress());
        return customerAddresses;
    }


    @GetMapping("list")
    public String viewList(Model model) {
        model.addAttribute("visitLedgerList", visitLedgerService.getVisitLedgerList());
        List<String> collect = visitLedgerService.getVisitLedgerList().stream().map(s -> s.getAccountCardNumber()).collect(Collectors.toList());
        model.addAttribute("accountNoList",collect);//        new VisitLedgerEntity().setCreatedBy();
        return "collection/settings/visitLedger/visitledger";
    }

    @ResponseBody
    @GetMapping("list1")
        public List<VisitLedgerEntity> list(@RequestParam String accountNo) {
        List<VisitLedgerEntity> ledgers = visitLedgerService.findVisitLedgerEntityByAccountCardNumberAndStatus(accountNo);
        return ledgers;
    }

    @GetMapping("api/cardlist/{account_no}")
    @ResponseBody
    public List<String> getJsonList(@PathVariable("account_no") String accountNumber){
        return cardRepository
                .findByAccountCardNumberLike(accountNumber)
                .stream().map(v -> v.getCardNo()).collect(Collectors.toList());
    }

    @GetMapping("api/loanlist/{account_no}")
    @ResponseBody
    public List<String> getLoanAccount(@PathVariable("account_no") String accountNumber) {
        return loanRepository
                .findByAccountCardNumberLike(accountNumber)
                .stream().map(s -> s.getAccountNo())
                .collect(Collectors.toList());

    }

    @GetMapping("create")
    public String createVisitLedger(Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("employeeList",gson.toJson(employeeRepository.findAll()));
        model.addAttribute("sectorGroupList", gson.toJson(employeeService.getAll()));
        List<EmployeeInfoEntity> employeeInfoEntities = employeeService.getAll();

        model.addAttribute("visitLedger",new VisitLedgerEntity());
        model.addAttribute("district",districtService.getAll());

        return "collection/settings/visitLedger/create";
    }


    @PostMapping("create/visit-ledger")
    @ResponseBody
    public VisitLedgerEntity createVisitLedgerFor360(VisitLedgerEntity visitLedgerEntity){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        visitLedgerEntity.setUsername(user.getUsername());
        visitLedgerEntity.setFirstName(user.getFirstName());
        visitLedgerEntity.setName(user.getUsername());
        visitLedgerEntity.setStatus(VisitLedgerStatus.PENDING);

        if(visitLedgerEntity.getId()== null) {
            visitLedgerEntity.setCreatedBy(user.getUsername());
            visitLedgerEntity.setCreatedDate(new Date());
            VisitLedgerEntity visitLedgerEntity1 = visitLedgerService.save(visitLedgerEntity);
            auditTrailService.saveCreatedData("Visit Ledger", visitLedgerEntity);
            return visitLedgerEntity1;
        }
        else {
            VisitLedgerEntity entity = visitLedgerRepository.getOne(visitLedgerEntity.getId());
            VisitLedgerEntity oldEntity = new VisitLedgerEntity();
            BeanUtils.copyProperties(entity, oldEntity);

            visitLedgerEntity.setModifiedBy(user.getUsername());
            visitLedgerEntity.setModifiedDate(new Date());
            VisitLedgerEntity visitLedgerEntity1 = visitLedgerService.save(visitLedgerEntity);
            auditTrailService.saveUpdatedData("Visit Ledger", oldEntity, visitLedgerEntity);
            return visitLedgerEntity1;
        }

    }


        //Confusing
    @PostMapping(value = "create")
    public String createVisitLedger (VisitLedgerEntity visitLedgerEntity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //System.out.println("visit ledger : "+visitLedgerEntity);

//        visitLedgerEntity.setName(user.getUsername());

        /*List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();
        for(int i=0 ; i<visitLedgerEntity.getMemberIds().size() ; i++){
            Optional<EmployeeInfoEntity> byId = employeeRepository.findById(Long.parseLong(visitLedgerEntity.getMemberIds().get(i)));
            if(byId.isPresent()) {
                employeeInfoEntities.add(byId.get());
            }
        }
        visitLedgerEntity.setVisitors(employeeInfoEntities);*/


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM YYYY");
        //SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd MMM YYYY");

        try {
            System.out.println(simpleDateFormat.parse(visitLedgerEntity.getVisitDate()));
            visitLedgerEntity.setDateOfVisit(simpleDateFormat.parse(visitLedgerEntity.getVisitDate()));
        } catch (ParseException e) {
            System.out.println(simpleDateFormat.format(visitLedgerEntity.getVisitDate()));
            e.printStackTrace();
        }

        visitLedgerEntity.setUsername(user.getUsername());
        visitLedgerEntity.setFirstName(user.getFirstName());
        visitLedgerEntity.setName(user.getUsername());
        visitLedgerEntity.setStatus(VisitLedgerStatus.PENDING);

        /*SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy");

        try{
            Date visitDate=formatter.parse(visitLedgerEntity.getVisitDate());
            visitLedgerEntity.setDateOfVisit(visitDate);
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/
        if(visitLedgerEntity.getId()== null) {

            List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();

            for(String s: visitLedgerEntity.getEmployeeId()){
                EmployeeInfoEntity employeeInfo= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfo);
            }

            visitLedgerEntity.setEmployee(employeeInfoEntities);


            visitLedgerEntity.setCreatedBy(user.getUsername());
            visitLedgerEntity.setCreatedDate(new Date());
            boolean save = visitLedgerService.saveVisitLedger(visitLedgerEntity);
            auditTrailService.saveCreatedData("Visit Ledger", visitLedgerEntity);
        }
        else {
            VisitLedgerEntity entity = visitLedgerRepository.getOne(visitLedgerEntity.getId());
            VisitLedgerEntity oldEntity = new VisitLedgerEntity();
            BeanUtils.copyProperties(entity, oldEntity);

            List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();

            for(String s: visitLedgerEntity.getEmployeeId()){
                EmployeeInfoEntity employeeInfo= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfo);
            }

            visitLedgerEntity.setEmployee(employeeInfoEntities);

            visitLedgerEntity.setModifiedBy(user.getUsername());
            visitLedgerEntity.setModifiedDate(new Date());

            visitLedgerEntity.setAccountCardNumber(oldEntity.getAccountCardNumber());

            boolean update = visitLedgerService.updateVisitLedger(visitLedgerEntity);
            auditTrailService.saveUpdatedData("Visit Ledger", oldEntity, visitLedgerEntity);
        }

        return "redirect:/collection/visitledger/list";

        //Confusing
    }

    @GetMapping("edit")
    public String editVistLedger(@RequestParam(value = "id") Long visitLedgerId, Model model) {
        VisitLedgerEntity VisitLedgerEntityById = visitLedgerService.getByID(visitLedgerId);
        model.addAttribute("visitLedger",VisitLedgerEntityById);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("employeeList",gson.toJson(employeeRepository.findAll()));
        model.addAttribute("selectedEmployeeList",gson.toJson(VisitLedgerEntityById.getEmployee()));
        model.addAttribute("district",districtService.getAll());
        return "collection/settings/visitLedger/create";
    }

    @GetMapping("view")
    public String viewVisitLedger(@RequestParam(value = "id") Long visitLedgerId,Model model) {
        model.addAttribute("visitLedger",visitLedgerService.getByID(visitLedgerId));

        return "collection/settings/visitLedger/view";
    }


    @GetMapping("find")
    @ResponseBody
    public VisitLedgerEntity findVisitLedgerById(@RequestParam Long id){
        VisitLedgerEntity visitLedgerEntity = visitLedgerService.getByID(id);
        return visitLedgerEntity;
    }


    @GetMapping("teamleader/find")
    @ResponseBody
    public List<VisitLedgerEntity> findVisitLedgerByCreatedBy(){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(user.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());
        List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();
        for (PeopleAllocationLogicInfo logicInfo: peopleAllocationLogicInfoList){
            List<VisitLedgerEntity> visitLedgerEntities = visitLedgerService.findVisitLedgerEntityByCreatedByAndStatus(logicInfo.getDealer().getPin());
            if (!visitLedgerEntities.isEmpty()){
                visitLedgerEntityList.addAll(visitLedgerEntities);
            }
        }
        return visitLedgerEntityList;
    }

    @GetMapping("find/visit-ledger")
    @ResponseBody
    public VisitLedgerEntity findVisitLedgerEntityById(@RequestParam Long id){
        VisitLedgerEntity visitLedgerEntity = visitLedgerService.findVisitLedgerEntityById(id);
        return visitLedgerEntity;
    }

    @GetMapping("save/accepted")
    @ResponseBody
    public VisitLedgerEntity changeVisitLedgerStatusToAccepted(@RequestParam Long id){
        VisitLedgerEntity visitLedgerEntity = visitLedgerService.findVisitLedgerEntityById(id);
        visitLedgerEntity.setStatus(VisitLedgerStatus.ACCEPTED);
        VisitLedgerEntity entity = visitLedgerService.save(visitLedgerEntity);
        return entity;
    }

    @GetMapping("save/decline")
    @ResponseBody
    public VisitLedgerEntity changeVisitLedgerStatusToDecline(@RequestParam Long id){
        VisitLedgerEntity visitLedgerEntity = visitLedgerService.findVisitLedgerEntityById(id);
        visitLedgerEntity.setStatus(VisitLedgerStatus.DECLINE);
        VisitLedgerEntity entity = visitLedgerService.save(visitLedgerEntity);
        return entity;
    }



    @GetMapping("supervisor/find")
    @ResponseBody
    public List<VisitLedgerEntity> findVisitLedgerForSuperVisorByCreatedBy(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity entity = employeeRepository.findByPin(userPrincipal.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findTeamLeadBySupervisorId(entity.getId());
        List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();
        for (PeopleAllocationLogicInfo logicInfo: peopleAllocationLogicInfoList){
            List<VisitLedgerEntity> visitLedgerEntities = visitLedgerService.findVisitLedgerEntityByCreatedByAndStatusForSupervisor(logicInfo.getDealer().getPin());
            if (!visitLedgerEntities.isEmpty()){
                visitLedgerEntityList.addAll(visitLedgerEntities);
            }
        }
        return visitLedgerEntityList;
    }


    @GetMapping("supervisor/save/accepted")
    @ResponseBody
    public VisitLedgerEntity changeVisitLedgerStatusToAcceptedFromSupervisor(@RequestParam Long id){
        VisitLedgerEntity visitLedgerEntity = visitLedgerService.findVisitLedgerEntityById(id);
        visitLedgerEntity.setStatus(VisitLedgerStatus.ACCEPTED);
        VisitLedgerEntity entity = visitLedgerService.save(visitLedgerEntity);
        return entity;
    }


    @GetMapping("supervisor/save/decline")
    @ResponseBody
    public VisitLedgerEntity changeVisitLedgerStatusToDeclineFromSupervisor(@RequestParam Long id){
        VisitLedgerEntity visitLedgerEntity = visitLedgerService.findVisitLedgerEntityById(id);
        visitLedgerEntity.setStatus(VisitLedgerStatus.DECLINE);
        VisitLedgerEntity entity = visitLedgerService.save(visitLedgerEntity);
        return entity;
    }

}
