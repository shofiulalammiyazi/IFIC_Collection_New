package com.unisoft.customerloanprofile.contactInfo;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContactInfoService {

    @Autowired
    private ContactInfoDao contactInfoDao;

    @Autowired
    private AuditTrailService auditTrailService;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private ContactInfoDtoRepository contactInfoDtoRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;



    public List<ContactInfo> getContactInfoList(Long cusId){

        return contactInfoDao.getList(cusId);
    }

    public boolean saveContactInfo(ContactInfo contactInfo){
        boolean isNewEntity = true;

        ContactInfo oldEntity = new ContactInfo();
        if(contactInfo.getId()!=null){
            ContactInfo contactInfo1 = contactInfoDao.getById(contactInfo.getId());
            BeanUtils.copyProperties(contactInfo1, oldEntity);

            isNewEntity = false;
        }

        boolean response = contactInfoDao.save(contactInfo);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Contact Info", contactInfo);
        else
            auditTrailService.saveUpdatedData("Contact Info", oldEntity, contactInfo);
        return response;

        }


    public List<ContactInfoDto> findCurrentMonthContactInfoByDealerPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoDtoRepository.findCurrentMonthContactInfoByDealerPin(startDate,endDate,pin);
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoDto contactInfoDto = new ContactInfoDto();
            contactInfoDto.setAccountNo(tuple.get("accountNo"));
            contactInfoDto.setCustomerId(tuple.get("customerId"));
            contactInfoDto.setAttempted(tuple.get("attempted"));
//            contactInfoDto.setUnattempted(tuple.get("unattempted"));
            contactInfoDtoList.add(contactInfoDto);
        }

        return contactInfoDtoList;
    }



    public List<ContactInfoDto> findCurrentMonthContactInfoByCardDealerPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoDtoRepository.findCurrentMonthContactInfoByCardDealerPin(startDate,endDate,pin);
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoDto contactInfoDto = new ContactInfoDto();
            contactInfoDto.setAccountNo(tuple.get("accountNo"));
            contactInfoDto.setCustomerId(tuple.get("customerId"));
            contactInfoDto.setAttempted(tuple.get("attempted"));
//            contactInfoDto.setUnattempted(tuple.get("unattempted"));
            contactInfoDtoList.add(contactInfoDto);
        }

        return contactInfoDtoList;
    }

    public List<ContactInfoDto> findCurrentMonthContactInfoByTeamleadPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoDtoRepository.findCurrentMonthContactInfoByTeamleadPin(startDate,endDate,pin);
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoDto contactInfoDto = new ContactInfoDto();
            contactInfoDto.setAccountNo(tuple.get("accountNo"));
            contactInfoDto.setCustomerId(tuple.get("customerId"));
            contactInfoDto.setAttempted(tuple.get("attempted"));
//            contactInfoDto.setUnattempted(tuple.get("unattempted"));
            contactInfoDtoList.add(contactInfoDto);
        }

        return contactInfoDtoList;
    }

    public List<ContactInfoDto> findCardCurrentMonthContactInfoByTeamleadPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoDtoRepository.findCardCurrentMonthContactInfoByTeamleadPin(startDate,endDate,pin);
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoDto contactInfoDto = new ContactInfoDto();
            contactInfoDto.setAccountNo(tuple.get("accountNo"));
            contactInfoDto.setCustomerId(tuple.get("customerId"));
            contactInfoDto.setAttempted(tuple.get("attempted"));
            contactInfoDtoList.add(contactInfoDto);
        }

        return contactInfoDtoList;
    }

    public List<ContactInfoDto> findCurrentMonthContactInfoBySupervisorPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoDtoRepository.findCurrentMonthContactInfoBySupervisorPin(startDate,endDate,pin);
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoDto contactInfoDto = new ContactInfoDto();
            contactInfoDto.setAccountNo(tuple.get("accountNo"));
            contactInfoDto.setCustomerId(tuple.get("customerId"));
            contactInfoDto.setAttempted(tuple.get("attempted"));
//            contactInfoDto.setUnattempted(tuple.get("unattempted"));
            contactInfoDtoList.add(contactInfoDto);
        }

        return contactInfoDtoList;
    }

    public List<ContactInfoDto> findCurrentMonthContactInfoByManagerPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoDtoRepository.findCurrentMonthContactInfoByManagerPin(startDate,endDate,pin);
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoDto contactInfoDto = new ContactInfoDto();
            contactInfoDto.setAccountNo(tuple.get("accountNo"));
           // contactInfoDto.setCustomerId(tuple.get("customerId"));
            contactInfoDto.setAttempted(tuple.get("attempted"));
//            contactInfoDto.setUnattempted(tuple.get("unattempted"));
            contactInfoDtoList.add(contactInfoDto);
        }

        return contactInfoDtoList;
    }

    public List<ContactInfo> findAttemptCallListByCustomerId(Long customerId) {
        return contactInfoRepository.findAttemptCallListByCustomerId(customerId);
    }

    public List<ContactInfo> findUnAttemptCallListByCustomerId(Long customerId) {
        return contactInfoRepository.findUnAttemptCallListByCustomerId(customerId);
    }
}
