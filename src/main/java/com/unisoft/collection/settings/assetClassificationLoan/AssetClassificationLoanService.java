package com.unisoft.collection.settings.assetClassificationLoan;


import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetClassificationLoanService {

    private final AssetClassificationLoanDao assetClassificationLoanDao;

    private final AssetClassificationLoanRepository repository;

    public List<AssetClassificationLoanEntity> getAll()
    {
        return assetClassificationLoanDao.getList();
    }

    public List<AssetClassificationLoanEntity> getActiveList()
    {
        return assetClassificationLoanDao.getActiveOnly();
    }

    public boolean saveNew(AssetClassificationLoanEntity paRqueueAccRuleLoan)
    {
        return assetClassificationLoanDao.saveNew(paRqueueAccRuleLoan);
    }

    public boolean updateAsset(AssetClassificationLoanEntity paRqueueAccRuleLoan)
    {
        return assetClassificationLoanDao.updateObj(paRqueueAccRuleLoan);
    }


    public AssetClassificationLoanEntity findOrSave(@NonNull AssetClassificationLoanEntity entity) {
        if (entity.getType() == null) return null;
        entity = Optional.ofNullable(repository.findByType(entity.getType().getName())).orElse(entity);
        if (entity.getId() == null) {
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            entity.setCreatedDate(new Date());
            entity.setCreatedBy(userPrincipal.getUsername());
            repository.save(entity);
        }
        return entity;
    }


    public AssetClassificationLoanEntity getById(Long id)
    {
        return assetClassificationLoanDao.getById(id);
    }
}
