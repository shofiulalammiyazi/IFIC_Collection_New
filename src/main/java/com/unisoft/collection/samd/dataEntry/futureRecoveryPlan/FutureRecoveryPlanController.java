package com.unisoft.collection.samd.dataEntry.futureRecoveryPlan;

import com.unisoft.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/samd/future-recovery-plan")
public class FutureRecoveryPlanController {

    private final FutureRecoveryPlanRepository repository;

    @GetMapping("/create")
    public FutureRecoveryPlan save(@RequestParam(value = "id", required = true) String accountNumber) {

        FutureRecoveryPlan futureRecoveryPlanEntity = new FutureRecoveryPlan();

        List<FutureRecoveryPlan> futureRecoveryPlanList = repository.findAllByAccountNumber(accountNumber);

        if (futureRecoveryPlanList.size() >0){
            futureRecoveryPlanEntity = futureRecoveryPlanList.get(0);
        }

        return futureRecoveryPlanEntity;
    }

    @PostMapping("/save")
    public FutureRecoveryPlan save(@Valid @RequestBody FutureRecoveryPlanPayload futureRecoveryPlanPayload) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        FutureRecoveryPlan futureRecoveryPlan = new FutureRecoveryPlan();

        FutureRecoveryPlan futureRecoveryPlanEntity = new FutureRecoveryPlan();

        List<FutureRecoveryPlan> initiativeActionTakenList = repository.findAllByAccountNumber(futureRecoveryPlanPayload.getAccount());

        if (initiativeActionTakenList.size() >0){
            futureRecoveryPlanEntity = initiativeActionTakenList.get(0);
        }


        if (futureRecoveryPlanEntity.getId() ==null){
            futureRecoveryPlan.setCreatedBy(user.getUsername());
            futureRecoveryPlan.setCreatedDate(new Date());

            futureRecoveryPlan.setAccountNumber(futureRecoveryPlanPayload.getAccount());
            futureRecoveryPlan.setFutureRecoveryPlan(futureRecoveryPlanPayload.getFutureRecoveryPlan());
            futureRecoveryPlan.setDealerId(user.getEmpId());

            repository.save(futureRecoveryPlan);
        }else {

            FutureRecoveryPlan oldEntity = repository.getOne(futureRecoveryPlanEntity.getId());
            FutureRecoveryPlan previousEntity = new FutureRecoveryPlan();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            previousEntity.setAccountNumber(futureRecoveryPlanPayload.getAccount());
            previousEntity.setFutureRecoveryPlan(futureRecoveryPlanPayload.getFutureRecoveryPlan());


            previousEntity.setCreatedBy(oldEntity.getCreatedBy());
            previousEntity.setCreatedDate(oldEntity.getCreatedDate());
            previousEntity.setModifiedBy(user.getUsername());
            previousEntity.setModifiedDate(new Date());
            repository.save(previousEntity);

        }

        return null;
    }


}
