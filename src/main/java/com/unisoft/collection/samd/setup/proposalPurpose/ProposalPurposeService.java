package com.unisoft.collection.samd.setup.proposalPurpose;

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
public class ProposalPurposeService implements CommonService<ProposalPurpose> {

    private final ProposalPurposeRepository proposalPurposeRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(ProposalPurpose data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (errorMessages.size() == 0){

            if(data.getId()==null){
                data.setCreatedBy(user.getUsername());
                data.setCreatedDate(new Date());
                proposalPurposeRepository.save(data);
                auditTrailService.saveCreatedData("Proposal Purpose", data);
            }else{
                ProposalPurpose oldEntity = proposalPurposeRepository.getOne(data.getId());
                ProposalPurpose previouseEntity = new ProposalPurpose();
                BeanUtils.copyProperties(oldEntity,previouseEntity);

                data.setModifiedBy(user.getUsername());
                data.setModifiedDate(new Date());
                proposalPurposeRepository.save(data);
                auditTrailService.saveUpdatedData("Proposal Purpose",previouseEntity,data);
            }

            proposalPurposeRepository.save(data);
            response.put("outcome", "success");
        }
        else{
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(ProposalPurpose data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(ProposalPurpose data) {
        return null;
    }

    @Override
    public ProposalPurpose findDataById(Long id) {
        ProposalPurpose purpose = proposalPurposeRepository.getOne(id);
        return purpose;
    }

    @Override
    public List<ProposalPurpose> findAllData() {
        List<ProposalPurpose> purposes = proposalPurposeRepository.findAll();
        return purposes;
    }

    @Override
    public List<String> validate(ProposalPurpose data) {
        ArrayList<String> errors = new ArrayList<>();

        if (Validation.isStringEmpty(data.getName())){
            errors.add("Purpose Name is required.");
        }

        return errors;
    }
}
