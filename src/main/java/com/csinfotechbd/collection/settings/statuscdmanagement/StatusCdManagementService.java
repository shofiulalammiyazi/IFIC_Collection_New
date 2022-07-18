package com.csinfotechbd.collection.settings.statuscdmanagement;

import com.csinfotechbd.audittrail.AuditTrailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusCdManagementService {

    StatusCdManagementRepository repository;
    private final AuditTrailService auditTrailService;

    public List<StatusCd> list() {
        return repository.findByEnabled(true);
    }

    public StatusCd findbyId(Long id) {
        return repository.findById(id).get();
    }

    public boolean save(StatusCd statusCd) {
        boolean isNewEntity = false;
        StatusCd previousEntity = new StatusCd();

        if (statusCd.getId() == null)
            isNewEntity = true;
        else{
            StatusCd oldEntity = repository.getOne(statusCd.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        repository.save(statusCd);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Status CD Management", statusCd);
        else
            auditTrailService.saveUpdatedData("Status CD Management", previousEntity, statusCd);
        return true;
    }

    ;

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }
}
