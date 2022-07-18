package com.csinfotechbd.legal.setup.legalExpense;

import com.csinfotechbd.legal.setup.lawyers.Lawyer;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LegalExpenseServiceImpl implements LegalExpenseService {

    private final LegalExpenseRepository repository;

    @Override
    public String save(LegalExpenseEntity legalExpenseEntity) {


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (legalExpenseEntity.getId() == null) {
            legalExpenseEntity.setCreatedBy(user.getUsername());
            legalExpenseEntity.setCreatedDate(new Date());
            repository.save(legalExpenseEntity);
        } else {
            LegalExpenseEntity oldEntity = repository.getOne(legalExpenseEntity.getId());
            LegalExpenseEntity previousEntity = new LegalExpenseEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            legalExpenseEntity.setCreatedBy(oldEntity.getCreatedBy());
            legalExpenseEntity.setCreatedDate(oldEntity.getCreatedDate());
            legalExpenseEntity.setModifiedBy(user.getUsername());
            legalExpenseEntity.setModifiedDate(new Date());
            repository.save(legalExpenseEntity);

        }
        return "1";
    }

    @Override
    public LegalExpenseEntity findById(Long id) {
        return repository.findById(id).orElse(new LegalExpenseEntity());
    }


    @Override
    public List<LegalExpenseEntity> findByEnabled(boolean enabled) {
        return repository.findByEnabled(enabled);
    }
}
