package com.csinfotechbd.collection.samd.dataEntry.justification;


import com.csinfotechbd.collection.samd.dataEntry.futureRecoveryPlan.FutureRecoveryPlan;
import com.csinfotechbd.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/samd/justification")
public class JustificationController {
    private final JustificationRepository repository;

    @GetMapping("/create")
    public Justification save(@RequestParam(value = "id", required = true) String accountNumber) {

        Justification justificationEntity = new Justification();

        List<Justification> justificationList = repository.findAllByAccountNumber(accountNumber);

        if (justificationList.size() >0){
            justificationEntity = justificationList.get(0);
        }

        return justificationEntity;
    }

    @PostMapping("/save")
    public Justification save(@Valid @RequestBody JustificationPayload justificationPayload) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Justification justification = new Justification();

        Justification justificationEntity = new Justification();

        List<Justification> justificationList = repository.findAllByAccountNumber(justificationPayload.getAccount());

        if (justificationList.size() >0){
            justificationEntity = justificationList.get(0);
        }


        if (justificationEntity.getId() ==null){
            justification.setCreatedBy(user.getUsername());
            justification.setCreatedDate(new Date());

            justification.setAccountNumber(justificationPayload.getAccount());
            justification.setJustification(justificationPayload.getJustification());
            justification.setDealerId(user.getEmpId());

            repository.save(justification);
        }else {

            Justification oldEntity = repository.getOne(justificationEntity.getId());
            Justification previousEntity = new Justification();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            previousEntity.setAccountNumber(justificationPayload.getAccount());
            previousEntity.setJustification(justificationPayload.getJustification());


            previousEntity.setCreatedBy(oldEntity.getCreatedBy());
            previousEntity.setCreatedDate(oldEntity.getCreatedDate());
            previousEntity.setModifiedBy(user.getUsername());
            previousEntity.setModifiedDate(new Date());
            repository.save(previousEntity);

        }

        return null;
    }

}
