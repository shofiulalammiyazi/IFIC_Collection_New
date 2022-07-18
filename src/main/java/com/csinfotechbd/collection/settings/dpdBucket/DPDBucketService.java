package com.csinfotechbd.collection.settings.dpdBucket;
/*
Created by Monirul Islam on 7/7/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DPDBucketService {

    private final DpdBucketRepository dpdBucketDao;
    private final AuditTrailService auditTrailService;

    public List<DPDBucketEntity> getAll() {
        return dpdBucketDao.findAll();
    }

    public String save(DPDBucketEntity dpdBucket) {
        boolean isNewEntity = false;
        DPDBucketEntity previousEntity = new DPDBucketEntity();

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dpdBucket.getId() == null) {
            if (alreadyExists(dpdBucket)) return "Bucket name already exist";
            dpdBucket.setCreatedBy(user.getUsername());
            dpdBucket.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            DPDBucketEntity oldEntity = dpdBucketDao.getOne(dpdBucket.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            dpdBucket.setModifiedBy(user.getUsername());
            dpdBucket.setModifiedDate(new Date());
        }
        dpdBucketDao.save(dpdBucket);

        if (isNewEntity)
            auditTrailService.saveCreatedData("DPD Bucket - Loan", dpdBucket);
        else
            auditTrailService.saveUpdatedData("DPD Bucket - Loan", previousEntity, dpdBucket);
        return "1";
    }

    public List<DPDBucketEntity> getActiveList() {
        return dpdBucketDao.findByEnabledOrderByBucketNameAsc(true);
    }

    public List<DPDBucketEntityDto1> getCustomActiveList() {
        List<DPDBucketEntity> byEnabledOrderByBucketNameAsc = dpdBucketDao.findByEnabledOrderByBucketNameAsc(true);
        List<DPDBucketEntityDto1> dpdBucketEntityDto1s = new ArrayList<>();

        for(DPDBucketEntity dbe : byEnabledOrderByBucketNameAsc){
            dpdBucketEntityDto1s.add(new DPDBucketEntityDto1(dbe));
        }

        return dpdBucketEntityDto1s.stream()
                .sorted(Comparator.comparingInt(DPDBucketEntityDto1::getBucketName))
                .collect(Collectors.toList());
    }

    public DPDBucketEntity getById(Long id) {
        return dpdBucketDao.findById(id).orElse(new DPDBucketEntity());
    }

    private boolean alreadyExists(DPDBucketEntity dpdBucket) {
        String bucketName = dpdBucket.getBucketName();
        if (dpdBucket.getId() == null) {
            return dpdBucketDao.existsByBucketName(bucketName);
        } else {
            DPDBucketEntity oldEntry = dpdBucketDao.findById(dpdBucket.getId()).orElse(new DPDBucketEntity());
            return (StringUtils.hasText(bucketName) &&
                    !bucketName.equals(oldEntry.getBucketName()) &&
                    dpdBucketDao.existsByBucketName(bucketName));
        }
    }

}
