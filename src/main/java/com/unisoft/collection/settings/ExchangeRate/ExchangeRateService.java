package com.unisoft.collection.settings.ExchangeRate;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    private EchangeRateRepository repository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<ExchangeRateEntity> getExchangeRateList() {
        return repository.findAll();
    }

    public String save(ExchangeRateEntity exchangeRateEntity ) {
        boolean isNewEntity = false;
        ExchangeRateEntity previousEntity = new ExchangeRateEntity();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (exchangeRateEntity.getId() == null) {
            exchangeRateEntity.setCreatedBy(user.getUsername());
            exchangeRateEntity.setCreatedDate(new Date());

        } else {
            ExchangeRateEntity oldData = repository.getOne(exchangeRateEntity.getId());
            BeanUtils.copyProperties(oldData, previousEntity);

            exchangeRateEntity.setModifiedBy(user.getUsername());
            exchangeRateEntity.setModifiedDate(new Date());
            exchangeRateEntity.setCreatedDate(previousEntity.getCreatedDate());

        }
        //
        setDefaultValueToZero();
        exchangeRateEntity.setLatest(true);
        repository.save(exchangeRateEntity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Exchange Rate", exchangeRateEntity);
        else
            auditTrailService.saveUpdatedData("Exchange Rate", previousEntity, exchangeRateEntity);
        return "1";
    }

    public void setDefaultValueToZero(){
        ExchangeRateEntity ex = repository.findExchangeRateLatestOld();

        if(ex != null) {
            ex.setLatest(false);
            repository.save(ex);
        }
    }

    public ExchangeRateEntity getById(Long id){

        return repository.findById(id).orElse(new ExchangeRateEntity());
    }

    public ExchangeRateEntity findExchaneRate() {
        return repository.findExchangeRateLatestOld();
    }
}
