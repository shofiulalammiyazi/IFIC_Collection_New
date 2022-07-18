package com.csinfotechbd.collection.automaticDistribution.samDistributionRule;
/*
Created by Monirul Islam at 8/8/2019
*/

import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SamDistributionService {

    @Autowired
    private SamDistributionRuleRepository repository;

    public boolean saveNew(SamDistributionRuleInfo agency) {
        return repository.save(agency).getId() != null;
    }

    public boolean updateAgency(SamDistributionRuleInfo agency) {
        return repository.save(agency).getId() != null;
    }

    public List<SamDistributionRuleInfo> getAll() {
        return repository.findAll();
    }

    public String save(SamDistributionRuleInfo samDistributionRuleInfo) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        samDistributionRuleInfo.setProductTypeEntityList(
                samDistributionRuleInfo.getSelectedProduct()
                        .stream()
                        .map(ProductTypeEntity::new)
                        .collect(Collectors.toList())
        );
        samDistributionRuleInfo.setDpdBucketEntities(
                samDistributionRuleInfo.getDpdBucketId()
                        .stream()
                        .map(DPDBucketEntity::new)
                        .collect(Collectors.toList())
        );
        if (samDistributionRuleInfo.getId() == null) {
            samDistributionRuleInfo.setCreatedBy(user.getUsername());
            samDistributionRuleInfo.setCreatedDate(new Date());
        } else {
            samDistributionRuleInfo.setModifiedBy(user.getUsername());
            samDistributionRuleInfo.setModifiedDate(new Date());
        }
        repository.save(samDistributionRuleInfo);
        return "1";
    }

    public SamDistributionRuleInfo getById(Long id) {
        if (id == null) {
            return Optional.ofNullable(repository.findFirstByOrderByIdDesc()).orElse(new SamDistributionRuleInfo());
        }
        return repository.getOne(id);
    }

    public List<SamDistributionRuleInfo> getActiveList() {
        return repository.findByEnabled(true);
    }
}
