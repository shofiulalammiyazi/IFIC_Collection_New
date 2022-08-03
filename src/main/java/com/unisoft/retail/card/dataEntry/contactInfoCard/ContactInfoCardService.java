package com.unisoft.retail.card.dataEntry.contactInfoCard;

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
public class ContactInfoCardService {

    @Autowired
    private ContactInfoCardDao contactInfoCardDao;

    @Autowired
    private ContactInfoCardRepository contactInfoCardRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private ContactInfoCardDtoRepository contactInfoCardDtoRepository;


    public List<ContactInfoCard> getContactInfoCardList(Long cusId){
        return contactInfoCardDao.getList(cusId);
    }

    public boolean saveContactInfoCard(ContactInfoCard contactInfoCard){
        boolean isNewEntity = true;

        ContactInfoCard oldEntity = new ContactInfoCard();
        if(contactInfoCard.getId()!=null){
            ContactInfoCard contactInfoCard1 = contactInfoCardDao.getById(contactInfoCard.getId());
            BeanUtils.copyProperties(contactInfoCard1, oldEntity);

            isNewEntity = false;
        }

        boolean response = contactInfoCardDao.save(contactInfoCard);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Contact Info-Card", contactInfoCard);
        else
            auditTrailService.saveUpdatedData("Contact Info-Card", oldEntity, contactInfoCard);
        return response;

    }


    public List<ContactInfoCard> findAttemptCallListByCustomerId(Long customerId) {
        return contactInfoCardRepository.findAttemptCallListByCustomerId(customerId);
    }

    public List<ContactInfoCard> findUnAttemptCallListByCustomerId(Long customerId){
        return contactInfoCardRepository.findUnAttemptCallListByCustomerId(customerId);
    }


    public List<ContactInfoCardDto> findCurrentMonthContactInfoByDealerPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        List<Tuple>tupleList = contactInfoCardDtoRepository.findCurrentMonthContactInfoCardByDealerPin(startDate,endDate,pin);
        List<ContactInfoCardDto> contactInfoCardDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            ContactInfoCardDto contactInfoCardDto = new ContactInfoCardDto();
            contactInfoCardDto.setAccountNo(tuple.get("accountNo"));
            contactInfoCardDto.setCustomerId(tuple.get("customerId"));
            contactInfoCardDto.setAttempted(tuple.get("attempted"));
//            contactInfoDto.setUnattempted(tuple.get("unattempted"));
            contactInfoCardDtoList.add(contactInfoCardDto);
        }

        return contactInfoCardDtoList;
    }

}
