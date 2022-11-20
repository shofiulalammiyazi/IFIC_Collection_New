package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService;

import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationDao;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountInformationService {

    @Autowired
    private AccountInformationRepository accountInformationRepository;
    @Autowired
    private AccountInformationDao accountInformationDao;



    public void getAccountInformationData(){

        List<AccountInformationDto> dataList = accountInformationDao.getData();
        List<AccountInformationEntity> accountInformationEntities = new ArrayList<>();


        for(AccountInformationDto dto:dataList ){

            AccountInformationEntity accountInformationEntity = new AccountInformationEntity();

            BeanUtils.copyProperties(dto, accountInformationEntity);

            accountInformationEntities.add(accountInformationEntity);

            if(accountInformationEntities.size() == 1000){
                accountInformationRepository.saveAll(accountInformationEntities);
                accountInformationEntities.clear();
            }


        }

        if(accountInformationEntities.size() > 0 || accountInformationEntities.size() < 1000){
            accountInformationRepository.saveAll(accountInformationEntities);
            accountInformationEntities.clear();
        }


    }


   
}
