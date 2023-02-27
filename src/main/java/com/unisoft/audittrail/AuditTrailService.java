package com.unisoft.audittrail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisoft.beans.Validation;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class AuditTrailService {

    @Autowired
    private AuditTrailDao auditTrailDao;

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public void saveCreatedData(String moduleName, Object presentData) {
        AuditTrail auditTrail = this.newAuditTrail(moduleName, presentData.getClass().getSimpleName());
        auditTrail.setPresentData(new Gson().toJson(presentData));
        auditTrail.setOperationType("Created");
        auditTrailRepository.save(auditTrail);
    }

    public void saveCreatedData1(String moduleName, Object presentData) {
        final Gson gson = new GsonBuilder().serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers()
                .create();
        AuditTrail auditTrail = this.newAuditTrail(moduleName, presentData.getClass().getSimpleName());
        auditTrail.setPresentData(gson.toJson(presentData));
        auditTrail.setOperationType("Created");
        auditTrailRepository.save(auditTrail);
    }

    public void saveUpdatedData(String moduleName, Object previosData, Object presentData) {
        AuditTrail auditTrail = this.newAuditTrail(moduleName, presentData.getClass().getSimpleName());
        auditTrail.setPreviousData(new Gson().toJson(previosData));
        auditTrail.setPresentData(new Gson().toJson(presentData));
        auditTrail.setOperationType("Updated");
        auditTrailRepository.save(auditTrail);
    }



    public void saveUpdatedData1(String moduleName, Object previousData, Object presentData) {
        AuditTrail auditTrail = this.newAuditTrail(moduleName, presentData.getClass().getSimpleName());

        final Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers()
                .create();
        try{
            auditTrail.setPreviousData(new Gson().toJson(previousData));
        }catch (Exception e){
            auditTrail.setPreviousData(gson.toJson(previousData));
        }

        try{
            auditTrail.setPresentData(new Gson().toJson(presentData));
        }catch (Exception e){
            auditTrail.setPresentData(gson.toJson(presentData));
        }

        auditTrail.setOperationType("Updated");
        auditTrailRepository.save(auditTrail);
    }





    public void saveDeletedData(String moduleName, Object presentData) {
        AuditTrail auditTrail = this.newAuditTrail(moduleName, presentData.getClass().getSimpleName());
        auditTrail.setPresentData(new Gson().toJson(presentData));
        auditTrail.setOperationType("Deleted");
        auditTrailRepository.save(auditTrail);
    }

    public List<AuditTrail> getAuditData() {
        return auditTrailRepository.findAll();
    }

    public AuditTrail getById(Long id) {
        return auditTrailDao.getById(id);
    }

    public List<AuditTrail> auditTrailList(Date startDate, Date endDate) {
        return auditTrailDao.rangeAuditList(startDate, endDate);
    }

    private AuditTrail newAuditTrail(String moduleName, String className) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setRequestIP(request.getRemoteAddr());
        auditTrail.setUsername(user.getUsername());

        String name = user.getFirstName();
        name += Validation.isStringEmpty(user.getFirstName())
                && Validation.isStringEmpty(user.getLastName()) ? "" : " ";
        name += user.getLastName();
        auditTrail.setName(name);

        auditTrail.setModuleName(moduleName);
        auditTrail.setClassName(className);

        return auditTrail;
    }

    public Page<AuditTrail> getAuditData(int pageNo) {

        int pageSize = 50;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return auditTrailRepository.findAll(pageable);
    }
}
