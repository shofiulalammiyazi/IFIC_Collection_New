package com.csinfotechbd.collection.finalSattlementAmount;

import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinalSattlementAmountService {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private FinalSattlementAmountRepository finalSattlementAmountRepository;

    public Map<String, Object> storeData(FinalSattlementAmount data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);

        if (errorMessages.size() == 0) {
            boolean isNewEntity = true;
            FinalSattlementAmount oldEntity = new FinalSattlementAmount();
            if (data.getId() != null) {
                FinalSattlementAmount entity = finalSattlementAmountRepository.getOne(data.getId());
                BeanUtils.copyProperties(entity, oldEntity);
                isNewEntity = false;
            }

            finalSattlementAmountRepository.save(data);
            if (isNewEntity)
                auditTrailService.saveCreatedData("Final Settlement Amount", data);
            else
                auditTrailService.saveUpdatedData("Final Settlement Amount", oldEntity, data);

            response.put("outcome", "success");
            response.put("finalSattlementAmount", data);
        } else {
            response.put("outcome", "failure");
        }
        return response;
    }

    public FinalSattlementAmount getByCustomerId(long customerid) {
        return finalSattlementAmountRepository.getByCustomerId(customerid);
    }

    public List<String> validate(FinalSattlementAmount data) {
        return new ArrayList<>();
    }
}
