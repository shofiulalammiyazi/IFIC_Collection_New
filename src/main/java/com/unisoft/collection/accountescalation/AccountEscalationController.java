package com.unisoft.collection.accountescalation;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/account-escalation")
public class AccountEscalationController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;

    @Autowired
    private AccountEscalationService escalationService;

    @Autowired
    private AccountEscalationRepository accountEscalationRepository;

    @Autowired
    private AccountEscalationNoteService accountEscalationNoteService;

    @Autowired
    private AccountInformationService accountInformationService;

    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;

    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getAccountEscalationByStatus() {

        Map<String, Object> map = new HashMap<String, Object>();

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());
        List<String> pinList = peopleAllocationLogicService.findDealerPinByAnyPin(employeeInfoEntity.getPin());
        pinList.add(employeeInfoEntity.getPin());
        List<AccountEscalation> escalationList = escalationService.findByToUserPinAndStatus(pinList);


        map.put("escalationList",escalationList);
        //map.put("count",escalationService.getCountByAccNo());

        return map;
    }

    @PostMapping("/scalate-from/team-leader")
    @ResponseBody
    public AccountEscalationDto getEscalationByIdForSupervisor(AccountEscalationDto accountEscalationDto) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());
        Long superVisorId = peopleAllocationLogicService.getSupperVisorPinByTeamleaderId(employeeInfoEntity.getId());
        EmployeeInfoEntity supperVisor = employeeService.getById(superVisorId);

        AccountEscalation accountEscalation = escalationService.findById(accountEscalationDto.getId());
        accountEscalation.setTeamLeaderPin(principal.getUsername());
        accountEscalation.setFromUserPin(accountEscalation.getToUserPin());
        accountEscalation.setFromUserName(accountEscalation.getToUserName());
        accountEscalation.setToUserPin(supperVisor.getPin());
        accountEscalation.setToUserName(supperVisor.getUser().getFirstName());
        accountEscalation.setStatus("Pending");

        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();

        BeanUtils.copyProperties(accountEscalation, accountEscalationHistory);

        AccountEscalation accountEscalation1 =  escalationService.save(accountEscalation);
        escalationService.saveHistory(accountEscalationHistory);

        AccountEscalationNote accountEscalationNote = new AccountEscalationNote(accountEscalationDto.getNote());
        accountEscalationNote.setStatus(accountEscalationDto.getStatus());
        accountEscalationNote.setAccountEscalation(accountEscalation1);
        accountEscalationNoteService.save(accountEscalationNote);
        return accountEscalationDto;
    }

    @PostMapping("/scalate-from/super-visor")
    @ResponseBody
    public AccountEscalationDto getEscalationByIdForManager(AccountEscalationDto accountEscalationDto) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());
        Long managerId = peopleAllocationLogicService.getManagerIdByEmployeeInfoEntityId(employeeInfoEntity.getId());
        EmployeeInfoEntity manager = employeeService.getById(managerId);

        AccountEscalation accountEscalation = escalationService.findById(accountEscalationDto.getId());
        accountEscalation.setSuperVisorPin(principal.getUsername());
        accountEscalation.setFromUserPin(accountEscalation.getToUserPin());
        accountEscalation.setFromUserName(accountEscalation.getToUserName());
        accountEscalation.setToUserPin(manager.getPin());
        accountEscalation.setToUserName(manager.getUser().getFirstName());

        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();
        BeanUtils.copyProperties(accountEscalation, accountEscalationHistory);

        AccountEscalation accountEscalation1 =  escalationService.save(accountEscalation);
        escalationService.saveHistory(accountEscalationHistory);

        AccountEscalationNote accountEscalationNote = new AccountEscalationNote(accountEscalationDto.getNote());
        accountEscalationNote.setStatus(accountEscalationDto.getStatus());
        accountEscalationNote.setAccountEscalation(accountEscalation1);
        accountEscalationNoteService.save(accountEscalationNote);

        return accountEscalationDto;
    }

    @PostMapping("/manager/escalation")
    @ResponseBody
    public AccountEscalationDto getEscalationForManager(AccountEscalationDto accountEscalationDto) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());

        AccountEscalation accountEscalation = escalationService.findById(accountEscalationDto.getId());

        accountEscalation.setToUserName(accountEscalation.getFromUserName());
        accountEscalation.setToUserPin(accountEscalation.getFromUserPin());

        accountEscalation.setManagerPin(principal.getUsername());

        accountEscalation.setFromUserName(employeeInfoEntity.getUser().getLastName());
        accountEscalation.setFromUserPin(employeeInfoEntity.getPin());
        accountEscalation.setApprovedBy(principal.getUsername()+"-"+principal.getFirstName());
        accountEscalation.setStatus("Resolved");

        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();
        BeanUtils.copyProperties(accountEscalation, accountEscalationHistory);

        AccountEscalation accountEscalation1 = escalationService.save(accountEscalation);

        escalationService.saveHistory(accountEscalationHistory);

        AccountEscalationNote accountEscalationNote = new AccountEscalationNote(accountEscalationDto.getNote());
        accountEscalationNote.setStatus(accountEscalationDto.getStatus());
        accountEscalationNote.setAccountEscalation(accountEscalation1);
        accountEscalationNoteService.save(accountEscalationNote);

        AccountInformationEntity accountInformationEntity = accountInformationService.getAccountInformation(accountEscalation.getAccountNumber());

        accountInformationEntity.setISEscalated("N");
        accountInformationEntity.setIsDistributed("N");
        accountInformationEntity.setIsSmsSent("N");

        accountInformationService.save(accountInformationEntity);

        LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findByAccountNoAndLatest(accountEscalation.getAccountNumber().substring(0,13),"1");

        loanAccountDistributionInfo.setLatest("0");

        loanAccountDistributionRepository.save(loanAccountDistributionInfo);

        return accountEscalationDto;
    }

    @PostMapping("/supervisor/resolved")
    @ResponseBody
    public AccountEscalationDto supervisorEscalationResolved(AccountEscalationDto accountEscalationDto) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());

        AccountEscalation accountEscalation = escalationService.findById(accountEscalationDto.getId());

        EmployeeInfoEntity employee = employeeService.getByPin(accountEscalation.getTeamLeaderPin());

        accountEscalation.setFromUserPin(employeeInfoEntity.getPin());
        accountEscalation.setFromUserName(employeeInfoEntity.getUser().getLastName());

        accountEscalation.setToUserName(employee.getUser().getLastName());
        accountEscalation.setToUserPin(employee.getPin());
        accountEscalation.setApprovedBy(principal.getUsername()+"-"+principal.getFirstName());
        accountEscalation.setStatus("Resolved");

        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();
        BeanUtils.copyProperties(accountEscalation, accountEscalationHistory);

        AccountEscalation accountEscalation1 = escalationService.save(accountEscalation);

        escalationService.saveHistory(accountEscalationHistory);

        AccountEscalationNote accountEscalationNote = new AccountEscalationNote(accountEscalationDto.getNote());
        accountEscalationNote.setStatus(accountEscalationDto.getStatus());
        accountEscalationNote.setAccountEscalation(accountEscalation1);
        accountEscalationNoteService.save(accountEscalationNote);

        AccountInformationEntity accountInformationEntity = accountInformationService.getAccountInformation(accountEscalation.getAccountNumber());

        accountInformationEntity.setISEscalated("N");
        accountInformationEntity.setIsDistributed("N");
        accountInformationEntity.setIsSmsSent("N");

        accountInformationService.save(accountInformationEntity);
        LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findByAccountNoAndLatest(accountEscalation.getAccountNumber().substring(0,13),"1");

        loanAccountDistributionInfo.setLatest("0");

        loanAccountDistributionRepository.save(loanAccountDistributionInfo);

        return accountEscalationDto;

    }


    @GetMapping("/escalation-list/by-dealer")
    @ResponseBody
    public List<AccountEscalation> getEscalationListForDealer() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());
        return escalationService.findAccountEscalationByDealerPin(employeeInfoEntity.getPin());
    }

    @GetMapping("/find/account-escalation")
    @ResponseBody
    public AccountEscalation getAccountEscalationById(Long id) {
        return escalationService.findById(id);
    }

    @PostMapping("/save")
    @ResponseBody
    public AccountEscalation saveAccountEscalatoin(AccountEscalation accountEscalation) {
        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();
        BeanUtils.copyProperties(accountEscalation,accountEscalationHistory);
        escalationService.saveHistory(accountEscalationHistory);
        return accountEscalationRepository.save(accountEscalation);
    }

    @PostMapping("/save/note")
    @ResponseBody
    public AccountEscalationDto createNote(AccountEscalationDto accountEscalationDto) {
//        AccountEscalation accountEscalation = new AccountEscalation(accountEscalationNote.getEscalationId());
//        accountEscalationNote.setAccountEscalation(accountEscalation);
//        accountEscalationNote.setStatus("Pending");
//        return accountEscalationNoteService.save(accountEscalationNote);

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());

        AccountEscalation escalation = escalationService.findById(accountEscalationDto.getId());
        EmployeeInfoEntity dealer = employeeService.getByPin(escalation.getDealerPin());

        escalation.setFromUserPin(employeeInfoEntity.getPin());
        escalation.setFromUserName(employeeInfoEntity.getUser().getLastName());

        escalation.setToUserName(dealer.getUser().getLastName());
        escalation.setToUserPin(dealer.getPin());
        escalation.setApprovedBy(principal.getUsername()+"-"+principal.getFirstName());
        escalation.setStatus("Resolved");

        AccountEscalationHistory accountEscalationHistory = new AccountEscalationHistory();
        BeanUtils.copyProperties(escalation, accountEscalationHistory);

        AccountEscalation accountEscalation = escalationService.save(escalation);

        escalationService.saveHistory(accountEscalationHistory);

        AccountEscalationNote accountEscalationNote = new AccountEscalationNote(accountEscalationDto.getNote());
        accountEscalationNote.setAccountEscalation(accountEscalation);
        accountEscalationNote.setStatus(accountEscalationDto.getStatus());
        accountEscalationNoteService.save(accountEscalationNote);

        AccountInformationEntity accountInformationEntity = accountInformationService.getAccountInformation(accountEscalation.getAccountNumber());

        accountInformationEntity.setISEscalated("N");
        accountInformationEntity.setIsDistributed("N");
        accountInformationEntity.setIsSmsSent("N");

        accountInformationService.save(accountInformationEntity);

        LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findByAccountNoAndLatest(accountEscalation.getAccountNumber().substring(0,13),"1");

        loanAccountDistributionInfo.setLatest("0");

        loanAccountDistributionRepository.save(loanAccountDistributionInfo);

        return accountEscalationDto;
    }

    @GetMapping("/find/account-escalation-note")
    @ResponseBody
    public List<AccountEscalationNote> getAccountEscalationNoteByAccountEscalationId(@RequestParam("accountEscalationId") Long accountEscalationId) {
        return accountEscalationNoteService.getAccountEscalationNoteByAccountEscalationId(accountEscalationId);
    }

    @GetMapping("/escalation/history/byaccno/{accNo}")
    @ResponseBody
    public List<AccountEscalationHistoryDto> getAllByAccNo(@PathVariable("accNo") String accNo){
        List<AccountEscalation> accountEscalations = accountEscalationRepository.findByAccountNumberOrderByCreatedDateDesc(accNo);

        List<AccountEscalationHistoryDto> accountEscalationHistoryDtos = new ArrayList<>();

        for(int i = 0; i<accountEscalations.size(); i++){
            AccountEscalationHistoryDto accountEscalationHistoryDto = new AccountEscalationHistoryDto();
            List<AccountEscalationNote> accountEscalationNote = new ArrayList<>();

            EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(accountEscalations.get(i).getFromUserPin());

            accountEscalationHistoryDto.setDesig(employeeInfoEntity.getDesignation().getName());
            accountEscalationHistoryDto.setCreatedDate(accountEscalations.get(i).getCreatedDate().toString().substring(0,10));
            accountEscalationHistoryDto.setStatus(accountEscalations.get(i).getStatus());
            accountEscalationHistoryDto.setDealerPin(accountEscalations.get(i).getDealerPin());
            accountEscalationHistoryDto.setSuperVisorPin(accountEscalations.get(i).getTeamLeaderPin());
            accountEscalationHistoryDto.setManagerPin(accountEscalations.get(i).getSuperVisorPin());
            accountEscalationHistoryDto.setSrManagerPin(accountEscalations.get(i).getManagerPin());

            accountEscalationNote = accountEscalationNoteService.getAccountEscalationNoteByAccountEscalationId(accountEscalations.get(i).getId());

            for(int j = 0; j<accountEscalationNote.size(); j++){
                EmployeeInfoEntity employeeInfoEntity1 = employeeService.getByPin(accountEscalationNote.get(j).getCreatedBy());

                if(employeeInfoEntity1.getDesignation().getName().equalsIgnoreCase("dealer")) {
                    accountEscalationHistoryDto.setDealerNote(accountEscalationNote.get(j).getNote());
                    accountEscalationHistoryDto.setDealerName(employeeInfoEntity1.getUser().getFirstName());
                }
                else if(employeeInfoEntity1.getDesignation().getName().equalsIgnoreCase("team leader")) {
                    accountEscalationHistoryDto.setSupervisorNote(accountEscalationNote.get(j).getNote());
                    accountEscalationHistoryDto.setSupervisorName(employeeInfoEntity1.getUser().getFirstName());
                }
                else if(employeeInfoEntity1.getDesignation().getName().equalsIgnoreCase("supervisor")) {
                    accountEscalationHistoryDto.setManagerNote(accountEscalationNote.get(j).getNote());
                    accountEscalationHistoryDto.setManagerName(employeeInfoEntity1.getUser().getFirstName());
                }
                else if(employeeInfoEntity1.getDesignation().getName().equalsIgnoreCase("manager")) {
                    accountEscalationHistoryDto.setSrManagerNote(accountEscalationNote.get(j).getNote());
                    accountEscalationHistoryDto.setSrManagerName(employeeInfoEntity1.getUser().getFirstName());
                }
            }
            accountEscalationHistoryDtos.add(accountEscalationHistoryDto);
        }

        return accountEscalationHistoryDtos;
    }

    @GetMapping("/count/{accNo}")
    @ResponseBody
    public Integer getAccountEscalationCount(@PathVariable("accNo") String accNo) {
        Integer countByAccNo = escalationService.getCountByAccNo(accNo);
        return countByAccNo;
    }


    @GetMapping("/find")
    @ResponseBody
    public List<AccountEscalation> findEscalationByAccountNo(String accountNo){
        return escalationService.findAccountEscalationByAccountNo(accountNo);
    }
}
