package com.csinfotechbd.collection.samd.setup.hrPosition;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.beans.Validation;
import com.csinfotechbd.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.csinfotechbd.common.CommonService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HrPositionService implements CommonService<HrPosition> {

    private final HrPositionRepository hrPositionRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(HrPosition data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (errorMessages.size() == 0){

            if(data.getId()==null){
                data.setCreatedBy(user.getUsername());
                data.setCreatedDate(new Date());
                hrPositionRepository.save(data);
                auditTrailService.saveCreatedData("Position", data);
            }else{
                HrPosition oldEntity = hrPositionRepository.getOne(data.getId());
                HrPosition previouseEntity = new HrPosition();
                BeanUtils.copyProperties(oldEntity,previouseEntity);

                data.setModifiedBy(user.getUsername());
                data.setModifiedDate(new Date());
                hrPositionRepository.save(data);
                auditTrailService.saveUpdatedData("Position",previouseEntity,data);

            }
            hrPositionRepository.save(data);
            response.put("outcome", "success");
        }
        else{
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(HrPosition data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(HrPosition data) {
        return null;
    }

    @Override
    public HrPosition findDataById(Long id) {
        HrPosition position = hrPositionRepository.getOne(id);
        return position;
    }

    @Override
    public List<HrPosition> findAllData() {
        List<HrPosition> positions = hrPositionRepository.findAll();
        return positions;
    }

    @Override
    public List<String> validate(HrPosition data) {
        ArrayList<String> errors = new ArrayList<>();

        if (Validation.isStringEmpty(data.getShortName())){
            errors.add("Position Short Name is required.");
        }

        if (Validation.isStringEmpty(data.getName())){
            errors.add("Position Name is required.");
        }

        return errors;
    }
}
