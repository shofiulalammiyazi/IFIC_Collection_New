package com.csinfotechbd.collection.samd.setup.proposalPreparedFor;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.beans.Validation;
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
public class ProposalPreparedReasonService implements CommonService<ProposalPreparedReason> {
    private final ProposalPreparedReasonRepository proposalPreparedReasonRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(ProposalPreparedReason data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);

        UserPrincipal user =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (errorMessages.size() == 0){

            if(data.getId()==null){
                data.setCreatedBy(user.getUsername());
                data.setCreatedDate(new Date());
                proposalPreparedReasonRepository.save(data);
                auditTrailService.saveCreatedData("Proposal Prepared Reason", data);
            }else{
                ProposalPreparedReason oldEntity = proposalPreparedReasonRepository.getOne(data.getId());
                ProposalPreparedReason previouseEntity = new ProposalPreparedReason();
                BeanUtils.copyProperties(oldEntity,previouseEntity);

                data.setModifiedBy(user.getUsername());
                data.setModifiedDate(new Date());
                proposalPreparedReasonRepository.save(data);
                auditTrailService.saveUpdatedData("Proposal Prepared Reason",previouseEntity,data);
            }
            proposalPreparedReasonRepository.save(data);
            response.put("outcome", "success");
        }
        else{
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(ProposalPreparedReason data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(ProposalPreparedReason data) {
        return null;
    }

    @Override
    public ProposalPreparedReason findDataById(Long id) {
        ProposalPreparedReason reason = proposalPreparedReasonRepository.getOne(id);
        return reason;
    }

    @Override
    public List<ProposalPreparedReason> findAllData() {
        List<ProposalPreparedReason> reasons = proposalPreparedReasonRepository.findAll();
        return reasons;
    }

    @Override
    public List<String> validate(ProposalPreparedReason data) {
        ArrayList<String> errors = new ArrayList<>();

        if (Validation.isStringEmpty(data.getName())){
            errors.add("Reason Name is required.");
        }

        return errors;
    }
}
