package com.csinfotechbd.collection.samd.dataEntry.reasonForClassification;

import com.csinfotechbd.collection.samd.dataEntry.backgroundOfTheBorrower.BackgroundOfTheBorrower;
import com.csinfotechbd.collection.samd.dataEntry.backgroundOfTheBorrower.BackgroundOfTheBorrowerPayload;
import com.csinfotechbd.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/samd/reason-for-classification")

public class ReasonForClassificationController {

    private final ReasonForClassificationRepository repository;

    @GetMapping("/create")
    public ReasonForClassification save(@RequestParam(value = "id", required = true) String accountNumber) {

        ReasonForClassification reasonForClassificationEntity = new ReasonForClassification();

        List<ReasonForClassification> reasonForClassificationList = repository.findAllByAccountNumber(accountNumber);

        if (reasonForClassificationList.size() >0){
            reasonForClassificationEntity = reasonForClassificationList.get(0);
        }

        return reasonForClassificationEntity;
    }



    @PostMapping("/save")
    public ReasonForClassification setBackgroundOfTheBorrower(@Valid @RequestBody ReasonForClassificationPayload reasonForClassificationPayload) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReasonForClassification reasonForClassification = new ReasonForClassification();

        ReasonForClassification reasonForClassificationEntity = new ReasonForClassification();

        List<ReasonForClassification> reasonForClassificationList = repository.findAllByAccountNumber(reasonForClassificationPayload.getAccount());

        if (reasonForClassificationList.size() >0){
            reasonForClassificationEntity = reasonForClassificationList.get(0);
        }


        if (reasonForClassificationEntity.getId() ==null){
            reasonForClassification.setCreatedBy(user.getUsername());
            reasonForClassification.setCreatedDate(new Date());

            reasonForClassification.setAccountNumber(reasonForClassificationPayload.getAccount());
            reasonForClassification.setBorrowerPointOfView(reasonForClassificationPayload.getBorrowerPointOfView());
            reasonForClassification.setBankPointOfView(reasonForClassificationPayload.getBankPointOfView());
            reasonForClassification.setDealerId(user.getEmpId());


            repository.save(reasonForClassification);
        }else {

            ReasonForClassification oldEntity = repository.getOne(reasonForClassificationEntity.getId());
            ReasonForClassification previousEntity = new ReasonForClassification();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            previousEntity.setAccountNumber(reasonForClassificationPayload.getAccount());
            previousEntity.setBorrowerPointOfView(reasonForClassificationPayload.getBorrowerPointOfView());
            previousEntity.setBorrowerPointOfView(reasonForClassificationPayload.getBankPointOfView());


            previousEntity.setCreatedBy(oldEntity.getCreatedBy());
            previousEntity.setCreatedDate(oldEntity.getCreatedDate());
            previousEntity.setModifiedBy(user.getUsername());
            previousEntity.setModifiedDate(new Date());
            repository.save(previousEntity);

        }

        return null;
    }

}
