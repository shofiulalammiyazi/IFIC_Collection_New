package com.unisoft.collection.reasonDelinquency;


import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReasonDelinquencyServiceImpl implements ReasonDelinquencyService{

    @Autowired
    private ReasonDelinquencyRepository reasonDelinquencyRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private ReasonDelinquencyDtoRepository reasonDelinquencyDtoRepository;

    @Autowired
    private ReasonDelinquencyWiseDtoRepository reasonDelinquencyWiseDtoRepository;


    @Override
    public ReasonDelinquency save(ReasonDelinquency reasonDelinquency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        reasonDelinquency.setDealerName(employeeInfoEntity.getUser().getFirstName()+" "+ employeeInfoEntity.getUser().getLastName());
        return reasonDelinquencyRepository.save(reasonDelinquency);
    }

    @Override
    public List<ReasonDelinquency> findReasonDelinquencyById(Long id) {
        return reasonDelinquencyRepository.findReasonDelinquenciesByCustomerId(id);
    }

    @Override
    public List<ReasonDelinquency> findByAccountNo(String accountNo){
        return reasonDelinquencyRepository.findReasonDelinquencyByAccountNo(accountNo);
    }

    public List<ReasonDelinquencyDto> findCurrentMonthReasonDelinquncyBySupervisorPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = reasonDelinquencyDtoRepository.findCurrentMonthReasonDelinquncyBySupervisorPin(startDate,endDate,pin);
        List<ReasonDelinquencyDto> reasonDelinquencyDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ReasonDelinquencyDto reasonDelinquencyDto = new ReasonDelinquencyDto();
            reasonDelinquencyDto.setAccountNo(tuple.get("accountNo"));
            reasonDelinquencyDto.setRfdName(tuple.get("rfdName"));
            reasonDelinquencyDto.setAmount(tuple.get("amount"));

            reasonDelinquencyDtoList.add(reasonDelinquencyDto);
        }

        return reasonDelinquencyDtoList;
    }

    public List<ReasonDelinquencyDto> findCurrentMonthReasonDelinquncyByManagerPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = reasonDelinquencyDtoRepository.findCurrentMonthReasonDelinquencyByManagerPin(startDate,endDate,pin);
        List<ReasonDelinquencyDto> reasonDelinquencyDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ReasonDelinquencyDto reasonDelinquencyDto = new ReasonDelinquencyDto();
            reasonDelinquencyDto.setAccountNo(tuple.get("accountNo"));
            reasonDelinquencyDto.setRfdName(tuple.get("rfdName"));
            reasonDelinquencyDto.setAmount(tuple.get("amount"));

            reasonDelinquencyDtoList.add(reasonDelinquencyDto);
        }

        return reasonDelinquencyDtoList;
    }

    public List<ReasonDelinquencyWiseDto> findCurrentMonthReasonDelinquncyWiseByDealerPin(String pin, String delinquency) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = reasonDelinquencyWiseDtoRepository.findCurrentMonthReasonDelinquencyWiseByDealerPin(startDate,endDate,pin, delinquency);
        List<ReasonDelinquencyWiseDto> reasonDelinquencyDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ReasonDelinquencyWiseDto reasonDelinquencyDto = new ReasonDelinquencyWiseDto();

            reasonDelinquencyDto.setAccountNo(tuple.get("accountNo"));
            reasonDelinquencyDto.setRfdName(tuple.get("rfdName"));
            reasonDelinquencyDto.setAccountName(tuple.get("accountName"));
            reasonDelinquencyDto.setDealerName(tuple.get("dealerName"));
            reasonDelinquencyDto.setOutstanding(tuple.get("outstanding"));


            reasonDelinquencyDtoList.add(reasonDelinquencyDto);
        }

        return reasonDelinquencyDtoList;
    }

    public List<ReasonDelinquencyWiseDto> getDealerWiseRfdBySupervisor(List<Long> dealerPins){
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Tuple> tupleList= reasonDelinquencyWiseDtoRepository.getDealerWiseRfdBySupervisor(startDate,endDate, user.getUsername(),dealerPins);
        List<ReasonDelinquencyWiseDto> reasonDelinquencyDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ReasonDelinquencyWiseDto reasonDelinquencyDto = new ReasonDelinquencyWiseDto();

            reasonDelinquencyDto.setAccountNo(tuple.get("accountNo"));
            reasonDelinquencyDto.setRfdName(tuple.get("rfdName"));
            reasonDelinquencyDto.setDealerName(tuple.get("dealerName"));
            reasonDelinquencyDto.setOutstanding(tuple.get("outstanding"));

            reasonDelinquencyDtoList.add(reasonDelinquencyDto);
        }

        return reasonDelinquencyDtoList;
    }

    public List<ReasonDelinquencyWiseDto> getDealerWiseRfdByManager(List<Long> dealerPins){
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Tuple> tupleList= reasonDelinquencyWiseDtoRepository.getDealerWiseRfdByManager(startDate,endDate, dealerPins);
        List<ReasonDelinquencyWiseDto> reasonDelinquencyDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ReasonDelinquencyWiseDto reasonDelinquencyDto = new ReasonDelinquencyWiseDto();

            reasonDelinquencyDto.setAccountNo(tuple.get("accountNo"));
            reasonDelinquencyDto.setRfdName(tuple.get("rfdName"));
            reasonDelinquencyDto.setDealerName(tuple.get("dealerName"));
            reasonDelinquencyDto.setOutstanding(tuple.get("outstanding"));

            reasonDelinquencyDtoList.add(reasonDelinquencyDto);
        }

        return reasonDelinquencyDtoList;
    }
}
