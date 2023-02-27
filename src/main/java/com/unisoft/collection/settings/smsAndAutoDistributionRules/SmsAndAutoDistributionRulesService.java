package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmsAndAutoDistributionRulesService {

    @Autowired
    private SmsAndAutoDistributionRulesRepository smsAndAutoDistributionRulesRepository;


    public List<SmsAndAutoDistributionRulesEntity> SmsAndAutoDistributionfindAll(){
        return smsAndAutoDistributionRulesRepository.findAll();
    }

    public List<SmsAndAutoDistributionRulesEntityDto> getByLoanStatusAndLoanType(String lt, String ls){
        List<SmsAndAutoDistributionRulesEntityDto> smsAndAutoDistributionRulesEntityDtos = new ArrayList<>();
        List<Tuple> tuples = smsAndAutoDistributionRulesRepository.findByLoanStatusEntityAndLoanTypeEntity(lt,ls);

        for(Tuple t : tuples){
            smsAndAutoDistributionRulesEntityDtos.add(
              new SmsAndAutoDistributionRulesEntityDto(t)
            );
        }

        return smsAndAutoDistributionRulesEntityDtos;
    }
    public SmsAndAutoDistributionRulesEntity getById(Long id)
    {
        return smsAndAutoDistributionRulesRepository.getOne(id);
    }

    public SmsAndAutoDistributionRulesEntity getByType(String type){
        return smsAndAutoDistributionRulesRepository.findByType(type);
    }

}
