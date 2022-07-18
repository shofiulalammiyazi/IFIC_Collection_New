package com.csinfotechbd.customerloanprofile.legalAction;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class LegalActionServiceImpl implements LegalActionService{


    @Autowired
    private LegalActionRepository legalActionRepository;

    @Override
    public List<LegalActionDto> getLegalAction(String accountNo) {
        List<Tuple>tupleList =  legalActionRepository.findLegalActionByAccountNo(accountNo);
        List<LegalActionDto> legalActionDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            LegalActionDto legalActionDto = new LegalActionDto();

            legalActionDto.setCaseNumber(tuple.get("caseNumber"));
            legalActionDto.setDateOfFiling(tuple.get("dateOfFiling"));
            legalActionDto.setSuitValue(tuple.get("suitValue"));
            legalActionDto.setCaseType(tuple.get("caseType"));
            legalActionDto.setNextDate(tuple.get("nextDate"));
            legalActionDto.setLegalExpense(tuple.get("legalExpense"));
            legalActionDto.setCourseOfAction(tuple.get("courseOfAction"));

            legalActionDtoList.add(legalActionDto);
        }
        return legalActionDtoList;
    }

}
