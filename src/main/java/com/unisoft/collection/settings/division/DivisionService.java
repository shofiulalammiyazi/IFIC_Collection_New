package com.unisoft.collection.settings.division;
/*
Created by   Islam at 6/23/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DivisionService {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private DivisionRepository divisionRepository;

    public List<DivisionEntity> getDivList() {
        return divisionRepository.findAll();
    }

    public String save(DivisionEntity division) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (alreadyExists(division)) return "Division code already exists";
        if (division.getDivId() == null) {
            division.setCreatedBy(user.getUsername());
            division.setCreatedDate(new Date());
            divisionRepository.save(division);
            auditTrailService.saveCreatedData("Division", division);
        } else {
            DivisionEntity oldEntry = divisionRepository.findById(division.getDivId()).orElse(null);
            DivisionEntity previousEntry = new DivisionEntity();
            BeanUtils.copyProperties(oldEntry, previousEntry);

            if (oldEntry == null) return "Failed to update. No existing data found";
            division.setCreatedBy(oldEntry.getCreatedBy());
            division.setCreatedDate(oldEntry.getCreatedDate());
            division.setModifiedBy(user.getUsername());
            division.setModifiedDate(new Date());
            divisionRepository.save(division);
            auditTrailService.saveUpdatedData("Division", previousEntry, division);
        }
        return "1";
    }

    public DivisionEntity getById(Long id) {
        return divisionRepository.findById(id).orElse(new DivisionEntity());
    }

    public List<DivisionEntity> getActiveList() {
        return divisionRepository.findByEnabled(true);
    }

    private boolean alreadyExists(DivisionEntity division) {
        if (division.getDivId() == null) {
            return divisionRepository.existsByDivCode(division.getDivCode());
        } else {
            DivisionEntity oldEntry = divisionRepository.findById(division.getDivId()).orElse(new DivisionEntity());
            return !division.getDivCode().equals(oldEntry.getDivCode()) && divisionRepository.existsByDivCode(division.getDivCode());
        }
    }
}
