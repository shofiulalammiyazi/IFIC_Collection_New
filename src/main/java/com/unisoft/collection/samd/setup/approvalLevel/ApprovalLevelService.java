package com.unisoft.collection.samd.setup.approvalLevel;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.beans.Validation;
import com.unisoft.common.CommonService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ApprovalLevelService implements CommonService<ApprovalLevel> {

    private final ApprovalLevelRepository approvalLevelRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(ApprovalLevel data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (errorMessages.size() == 0){

            if(data.getId()==null){
                data.setCreatedBy(user.getUsername());
                data.setCreatedDate(new Date());
                approvalLevelRepository.save(data);
                auditTrailService.saveCreatedData("Approval Level",data);
            }else {
                ApprovalLevel oldEntity = approvalLevelRepository.getOne(data.getId());
                ApprovalLevel previewEntity = new ApprovalLevel();
                BeanUtils.copyProperties(oldEntity,previewEntity);

                data.setModifiedBy(user.getUsername());
                data.setModifiedDate(new Date());
                approvalLevelRepository.save(data);
                auditTrailService.saveUpdatedData("Approval Level", previewEntity, data);
            }

            response.put("outcome", "success");

        }
        else{
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(ApprovalLevel data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(ApprovalLevel data) {
        return null;
    }

    @Override
    public ApprovalLevel findDataById(Long id) {
        ApprovalLevel level = approvalLevelRepository.getOne(id);
        return level;
    }

    @Override
    public List<ApprovalLevel> findAllData() {
        List<ApprovalLevel> levels = approvalLevelRepository.findAll();
        return levels;
    }

    @Override
    public List<String> validate(ApprovalLevel data) {
        ArrayList<String> errors = new ArrayList<>();

        if (Validation.isStringEmpty(data.getName())){
            errors.add("Level Name is required.");
        }

        return errors;
    }
}
